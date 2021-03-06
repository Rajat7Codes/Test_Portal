/**
 * 
 */
package com.iceico.testportal.Controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iceico.testportal.Exceptions.ResourceNotFoundException;
import com.iceico.testportal.Model.AddTest;
import com.iceico.testportal.Model.Options;
import com.iceico.testportal.Model.QuestionBank;
import com.iceico.testportal.Model.TestQuestion;
import com.iceico.testportal.Model.TestResult;
import com.iceico.testportal.Model.User;
import com.iceico.testportal.Service.AddTestService;
import com.iceico.testportal.Service.CompilerService;
import com.iceico.testportal.Service.QuestionBankService;
import com.iceico.testportal.Service.TestResultService;
import com.iceico.testportal.Service.UserService;

/**
 * @author Rajat
 * @version 0.1
 * 
 *          Created Date: 16/03/2020
 */
@Controller
public class StartTestController {
	public StartTestController() {

	}

	@Autowired
	private AddTestService addTestService;

	@Autowired
	private UserService userService;

	@Autowired
	private QuestionBankService questionBankService;

	@Autowired
	private CompilerService compilerService;

	@Autowired
	private TestResultService testResultService;

	/* JAVA STUDENT PANEL METHODS */

	/* TEST LIST PAGE */
	@RequestMapping("/java/student/test/list")
	public String testList(ModelMap modelMap, Locale locale) throws ResourceNotFoundException, ParseException {

		HashMap<Long, String> testList = new HashMap<Long, String>();
		for (AddTest addTest : this.addTestService.getAddTestList()) {
			for (TestResult result : testResultService.getTestResultList()) {
				if (this.userService.findBySSO(getPrincipal()).getId() == result.getUserId()) {
					if (addTest.getDepartmentName().equals("JAVA")) {
						testList.put(addTest.getAddTestId(), "not given");
					}
				}
			}
		}

		for (AddTest addTest : this.addTestService.getAddTestList()) {
			for (TestResult result : testResultService.getTestResultList()) {
				if (this.userService.findBySSO(getPrincipal()).getId() == result.getUserId()) {
					if (addTest.getDepartmentName().equals("JAVA")) {
						if (addTest.getAddTestId() == result.getTestId()) {
							testList.put(addTest.getAddTestId(), "given");
						}
					}
				}
			}
		}

		List<AddTest> addTestList = new ArrayList<AddTest>();
		if (!testList.isEmpty()) {
			for (Long testId : testList.keySet()) {
				if (testList.get(testId).equals("not given")) {
					AddTest test = this.addTestService.getAddTestById(testId);
					addTestList.add(test);
				}
			}
		} else {
			for (AddTest test : addTestService.getAddTestList()) {
				if (test.getDepartmentName().equals("JAVA")) {
					addTestList.add(test);
				}
			}
		}

		Collections.reverse(addTestList);
		User user = this.userService.findBySSO(this.getPrincipal());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("testListShown", addTestList);
		return "j_testList";

	}

	/* START TEST PAGE */
	@RequestMapping("/java/student/start/test/{testId}")
	public String startTest(@PathVariable("testId") @Valid Long testId, ModelMap modelMap, Locale locale)
			throws ResourceNotFoundException, ParseException {

		AddTest test = addTestService.getAddTestById(testId);
		modelMap.addAttribute("addTest", test);

		List<QuestionBank> questionDetailList = new ArrayList<QuestionBank>();

		for (TestQuestion question : test.getTestQuestions()) {
			questionDetailList.add(this.questionBankService.getQuestionBankById(question.getQuestionId()));
		}

		modelMap.addAttribute("testQuestions", questionDetailList);
		modelMap.addAttribute("user", this.userService.findBySSO(this.getPrincipal()));
		return "j_startTest";
	}

	/* COMPILE TEST */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/java/student/start/test/compiler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject runCode(@RequestParam("language") String languageIn,
			@RequestParam("code") String code, @RequestParam("quesId") Long quesId)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		QuestionBank question = this.questionBankService.getQuestionBankById(quesId);
		String response = this.compilerService.runCodeWithInput(languageIn, code, question.getSampleInput());
		JSONObject resObj = (JSONObject) new JSONParser().parse(response);

		JSONObject outObj = new JSONObject();

		if (question.getSampleOutput().equals(resObj.get("output"))
				|| question.getSampleOutput().equals("\n" + resObj.get("output"))) {
			outObj.put("testCase", "successfull");
		} else {
			outObj.put("testCase", "failed");
		}
		outObj.put("output", resObj.get("output"));

		return outObj;
	}

	/* END TEST */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "/java/student/end/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject endTest(@RequestParam("QnA") String qnA, @RequestParam("testName") String testName,
			@RequestParam("testId") Long testId, ModelMap modelMap)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		double totalMarks = 0;
		String yourAnswers = "";

		AddTest addTest = this.addTestService.getAddTestById(testId);

		TestResult testResult = new TestResult();
		for (TestQuestion testQuestion : this.addTestService.getAddTestById(testId).getTestQuestions()) {
			totalMarks += this.questionBankService.getQuestionBankById(testQuestion.getQuestionId()).getMarks();
		}

		JSONParser parser = new JSONParser();
		JSONArray allAnswers = (JSONArray) parser.parse(qnA);

		double obtainedMarks = 0;
		double wm = 0;
		double twm = 0;
		int attempted = allAnswers.size();

		for (int i = 0; i < allAnswers.size(); i++) {
			JSONObject answer = (JSONObject) allAnswers.get(i);
			QuestionBank question = this.questionBankService
					.getQuestionBankById(Long.parseLong(answer.get("questionId") + ""));

			// Checks if question type is coding
			if (question.getQuestionType().getProgramType()) {

				String code = answer.get("code") + "";
				String lang = answer.get("lang") + "";
				String input = question.getHiddenInput();

				String response = this.compilerService.runCodeWithInput(lang, code, input);
				JSONObject resObj = (JSONObject) new JSONParser().parse(response);

				if (yourAnswers.isBlank())
					yourAnswers += question.getQuestionBankId()+"&"+resObj.get("output");
				else
					yourAnswers += "," + question.getQuestionBankId()+"&"+resObj.get("output");

				if (question.getHiddenOutput().equals(resObj.get("output"))
						|| question.getHiddenOutput().equals("\n" + resObj.get("output"))) {
					obtainedMarks += Integer.parseInt(answer.get("marks").toString());
					System.out.println(obtainedMarks);
				} else {
					// Below code check whether question has negative marking or not
					if (addTest.getNegativeMarking()) {
						String ratio = addTest.getRatio();
						String array[] = ratio.split("/");
						wm = Integer.parseInt(answer.get("marks").toString());
						int r = 0;
						for (String temp : array) {
							array[r] = temp;
							r++;
						}

						wm = wm * (Integer.parseInt(array[0]));
						wm = wm / (Integer.parseInt(array[1]));
						twm += wm;
					}
				}

			} else {
				for (Options opt : question.getOptions()) {

					if (opt.getOptionsId() == Long.parseLong(answer.get("optionId") + "")) {
						Boolean userAnswer = opt.getCorrectAnswer();
						if (yourAnswers.isBlank())
							yourAnswers += question.getQuestionBankId()+"&"+opt.getOptionName();
						else
							yourAnswers += "," + question.getQuestionBankId()+"&"+opt.getOptionName();

						if (userAnswer == true) {
							obtainedMarks += Integer.parseInt(answer.get("marks").toString());
						} else {
							// Below code check whether question has negative marking or not
							if (addTest.getNegativeMarking()) {
								wm = Integer.parseInt(answer.get("marks").toString());
								String ratio = addTest.getRatio();
								String array[] = ratio.split("/");
								int r = 0;
								for (String temp : array) {
									array[r] = temp;
									r++;
								}

								wm = wm * (Integer.parseInt(array[0]));
								wm = wm / (Integer.parseInt(array[1]));
								twm += wm;
							}
						}
					}
				}
			}
		}
		obtainedMarks -= twm;

		DecimalFormat form = new DecimalFormat("0.00");

		// Below code returns whether user is failed or passed
		double passingCriteria = this.addTestService.getAddTestById(testId).getPassingPercent();
		double per = (obtainedMarks / totalMarks) * 100;
		String result = null;

		Integer rank = 0;

		if (per >= passingCriteria) {
			result = "PASS";
		} else {
			result = "FAIL";
		}

		// Saving TestResult
		testResult.setAttempted(attempted);
		testResult.setObtainedMarks(obtainedMarks);
		testResult.setResultStatus(result);
		testResult.setTestName(testName);
		testResult.setTotalMarks(totalMarks);
		testResult.setDate(Calendar.getInstance().getTime());
		testResult.setTestId(testId);
		testResult.setAnswersGiven(yourAnswers);
		testResult.setUserId(this.userService.findBySSO(this.getPrincipal()).getId());
		testResult.setPercentage(Double.parseDouble(form.format(per)));
		testResult.setUserDepartmentName(
				this.userService.findBySSO(this.getPrincipal()).getDepartment().getDepartmentName());
		System.out.println("Department name ============>>>>>>"
				+ this.userService.findBySSO(this.getPrincipal()).getDepartment().getDepartmentName());
		this.testResultService.saveTestResult(testResult);

		JSONObject obj = new JSONObject();
		obj.put("testId", testResult.getTestResultId());
		return obj;
	}

	/* WEB STUDENT PANEL METHODS */

	/* TEST LIST PAGE */
	@RequestMapping("/web/student/test/list")
	public String testList_web(ModelMap modelMap, Locale locale) throws ResourceNotFoundException, ParseException {

		HashMap<Long, String> testList = new HashMap<Long, String>();

		for (AddTest addTest : this.addTestService.getAddTestList()) {
			for (TestResult result : testResultService.getTestResultList()) {
				if (this.userService.findBySSO(getPrincipal()).getId() == result.getUserId()) {
					if (addTest.getDepartmentName().equals("WEB")) {
						testList.put(addTest.getAddTestId(), "not given");
					}
				}
			}
		}

		for (AddTest addTest : this.addTestService.getAddTestList()) {
			for (TestResult result : testResultService.getTestResultList()) {
				if (this.userService.findBySSO(getPrincipal()).getId() == result.getUserId()) {
					if (addTest.getDepartmentName().equals("WEB")) {
						if (addTest.getAddTestId() == result.getTestId()) {
							testList.put(addTest.getAddTestId(), "given");
						}
					}
				}
			}
		}

		List<AddTest> addTestList = new ArrayList<AddTest>();
		if (!testList.isEmpty()) {
			for (Long testId : testList.keySet()) {
				if (testList.get(testId).equals("not given")) {
					addTestList.add(this.addTestService.getAddTestById(testId));
				}
			}
		} else {
			for (AddTest test : addTestService.getAddTestList()) {
				if (test.getDepartmentName().equals("WEB")) {
					addTestList.add(test);
				}
			}
		}

		System.out.println("Addtest =====> " + addTestList);
		Collections.reverse(addTestList);
		User user = this.userService.findBySSO(this.getPrincipal());
		modelMap.addAttribute("user", user);
		modelMap.addAttribute("testListShown", addTestList);

		return "w_testList";
	}

	/* START TEST PAGE */
	@RequestMapping("/web/student/start/test/{testId}")
	public String startTest_web(@PathVariable("testId") @Valid Long testId, ModelMap modelMap, Locale locale)
			throws ResourceNotFoundException, ParseException {

		AddTest test = addTestService.getAddTestById(testId);
		modelMap.addAttribute("addTest", test);

		List<QuestionBank> questionDetailList = new ArrayList<QuestionBank>();

		for (TestQuestion question : test.getTestQuestions()) {
			questionDetailList.add(this.questionBankService.getQuestionBankById(question.getQuestionId()));
		}

		modelMap.addAttribute("testQuestions", questionDetailList);
		modelMap.addAttribute("user", this.userService.findBySSO(this.getPrincipal()));
		return "j_startTest";
	}

	/* COMPILE TEST */
	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/web/student/start/test/compiler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject runCode_web(@RequestParam("language") String languageIn,
			@RequestParam("code") String code, @RequestParam("quesId") Long quesId)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		QuestionBank question = this.questionBankService.getQuestionBankById(quesId);
		String response = this.compilerService.runCodeWithInput(languageIn, code, question.getSampleInput());
		JSONObject resObj = (JSONObject) new JSONParser().parse(response);

		JSONObject outObj = new JSONObject();

		if (question.getSampleOutput().equals(resObj.get("output"))
				|| question.getSampleOutput().equals("\n" + resObj.get("output"))) {
			outObj.put("testCase", "successfull");
		} else {
			outObj.put("testCase", "failed");
		}
		outObj.put("output", resObj.get("output"));

		return outObj;
	}

	/* END TEST */
	@SuppressWarnings({ "deprecation", "unchecked" })
	@RequestMapping(value = "/web/student/end/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject endTest_web(@RequestParam("QnA") String qnA,
			@RequestParam("testName") String testName, @RequestParam("testId") Long testId, ModelMap modelMap)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		double totalMarks = 0;
		String yourAnswers = null;

		AddTest addTest = this.addTestService.getAddTestById(testId);

		TestResult testResult = new TestResult();
		for (TestQuestion testQuestion : this.addTestService.getAddTestById(testId).getTestQuestions()) {
			totalMarks += this.questionBankService.getQuestionBankById(testQuestion.getQuestionId()).getMarks();
		}

		JSONParser parser = new JSONParser();
		JSONArray allAnswers = (JSONArray) parser.parse(qnA);

		double obtainedMarks = 0;
		double wm = 0;
		double twm = 0;
		int attempted = allAnswers.size();

		for (int i = 0; i < allAnswers.size(); i++) {
			JSONObject answer = (JSONObject) allAnswers.get(i);
			QuestionBank question = this.questionBankService
					.getQuestionBankById(Long.parseLong(answer.get("questionId") + ""));

			// Checks if question type is coding
			if (question.getQuestionType().getProgramType()) {

				String code = answer.get("code") + "";
				String lang = answer.get("lang") + "";
				String input = question.getHiddenInput();

				String response = this.compilerService.runCodeWithInput(lang, code, input);
				JSONObject resObj = (JSONObject) new JSONParser().parse(response);

				if (yourAnswers == null)
					yourAnswers += resObj.get("output");
				else
					yourAnswers += "," + resObj.get("output");

				if (question.getHiddenOutput().equals(resObj.get("output"))
						|| question.getHiddenOutput().equals("\n" + resObj.get("output"))) {
					obtainedMarks += Integer.parseInt(answer.get("marks").toString());
					System.out.println(obtainedMarks);
				} else {
					// Below code check whether question has negative marking or not
					if (addTest.getNegativeMarking()) {
						String ratio = addTest.getRatio();
						String array[] = ratio.split("/");
						wm = Integer.parseInt(answer.get("marks").toString());
						int r = 0;
						for (String temp : array) {
							array[r] = temp;
							r++;
						}

						wm = wm * (Integer.parseInt(array[0]));
						wm = wm / (Integer.parseInt(array[1]));
						twm += wm;
					}
				}

			} else {
				for (Options opt : question.getOptions()) {

					if (opt.getOptionsId() == Long.parseLong(answer.get("optionId") + "")) {
						Boolean userAnswer = opt.getCorrectAnswer();
						if (yourAnswers == null)
							yourAnswers += opt.getOptionName();
						else
							yourAnswers += "," + opt.getOptionName();

						if (userAnswer == true) {
							obtainedMarks += Integer.parseInt(answer.get("marks").toString());
						} else {
							// Below code check whether question has negative marking or not
							if (addTest.getNegativeMarking()) {
								wm = Integer.parseInt(answer.get("marks").toString());
								String ratio = addTest.getRatio();
								String array[] = ratio.split("/");
								int r = 0;
								for (String temp : array) {
									array[r] = temp;
									r++;
								}

								wm = wm * (Integer.parseInt(array[0]));
								wm = wm / (Integer.parseInt(array[1]));
								twm += wm;
							}
						}
					}
				}
			}
		}
		obtainedMarks -= twm;

		// Below code returns whether user is failed or passed
		double passingCriteria = this.addTestService.getAddTestById(testId).getPassingPercent();
		double per = (obtainedMarks / totalMarks) * 100;
		String result = null;

		Integer rank = 0;

		if (per >= passingCriteria) {
			result = "PASS";
		} else {
			result = "FAIL";
		}

		// Saving TestResult
		testResult.setAttempted(attempted);
		testResult.setObtainedMarks(obtainedMarks);
		testResult.setResultStatus(result);
		testResult.setTestName(testName);
		testResult.setTotalMarks(totalMarks);
		testResult.setDate(Calendar.getInstance().getTime());
		testResult.setTestId(testId);
		testResult.setAnswersGiven(yourAnswers);
		testResult.setUserId(this.userService.findBySSO(this.getPrincipal()).getId());
		testResult.setPercentage(per);
		this.testResultService.saveTestResult(testResult);

		JSONObject obj = new JSONObject();
		obj.put("testId", testResult.getTestResultId());
		return obj;
	}

	/**
	 * 
	 */
	private String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
}
