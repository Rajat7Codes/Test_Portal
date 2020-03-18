/**
 * 
 */
package com.iceico.testportal.Controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.iceico.testportal.Service.AddTestService;
import com.iceico.testportal.Service.CompilerService;
import com.iceico.testportal.Service.QuestionBankService;
import com.iceico.testportal.Service.TestResultService;
import com.iceico.testportal.Service.UserService;

/**
 * @author Rajat
 * @version 0.1 Creation Date: 16/03/2020
 *
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

	/* Test List page */
	@RequestMapping("/java/student/test/list")
	public String testList(ModelMap modelMap, Locale locale) throws ResourceNotFoundException, ParseException {
		modelMap.addAttribute("user", this.userService.findBySSO(this.getPrincipal()));
		modelMap.addAttribute("testList", this.addTestService.getAddTestList());
		return "testList";
	}

	/* Start Test page */
	@RequestMapping("/java/student/start/test/{testId}")
	public String startTest(@PathVariable("testId") @Valid Long testId, ModelMap modelMap, Locale locale)
			throws ResourceNotFoundException, ParseException {

		AddTest test = addTestService.getAddTestById(testId);
		modelMap.addAttribute("addTest", test);
		// System.out.println("");
		List<QuestionBank> questionDetailList = new ArrayList<QuestionBank>();

		for (TestQuestion question : test.getTestQuestions()) {
			questionDetailList.add(this.questionBankService.getQuestionBankById(question.getQuestionId()));
		}

		modelMap.addAttribute("testQuestions", questionDetailList);
		modelMap.addAttribute("user", this.userService.findBySSO(this.getPrincipal()));
		return "startTest";
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/java/student/start/test/compiler", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject runCode(@RequestParam("language") String languageIn,
			@RequestParam("code") String code)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		String response = this.compilerService.runCode(languageIn, code);
		JSONObject resObj = (JSONObject) new JSONParser().parse(response);
		System.out.println( resObj.get("output"));
		return resObj;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@RequestMapping(value = "/java/student/end/test", produces = MediaType.APPLICATION_JSON_UTF8_VALUE, method = RequestMethod.GET)
	public @ResponseBody JSONObject endTest(@RequestParam("QnA") String qnA, @RequestParam("testName") String testName,
			@RequestParam("testId") Long testId, ModelMap modelMap)
			throws ResourceNotFoundException, ParseException, org.json.simple.parser.ParseException {

		double totalMarks = 0;
		
		TestResult testResult = new TestResult();
		for (TestQuestion testQuestion : this.addTestService.getAddTestById(testId).getTestQuestions()) {
			totalMarks += this.questionBankService.getQuestionBankById(testQuestion.getQuestionId()).getMarks();
		}
		
		JSONParser parser = new JSONParser();
		JSONArray allAnswers = (JSONArray) parser.parse(qnA);
		
		double obtainedMarks = 0;
		int attempted = allAnswers.size();

		for (int i = 0; i < allAnswers.size(); i++) {
			JSONObject answer = (JSONObject) allAnswers.get(i);
			QuestionBank question = this.questionBankService.getQuestionBankById(Long.parseLong(answer.get("questionId") + ""));
			
			
			// Checks if question type is coding 
			if(question.getQuestionType().getProgramType()) {
//				if(  question.getHiddenInput() == question.);
			
				System.out.println("================= In Program type code =================");
				String code = answer.get("code")+"";
				String lang = answer.get("lang")+"";
				String input = question.getHiddenInput();
				
				String response = this.compilerService.runCodeWithInput(lang, code, input);
				System.out.println("Response = "+response);
				JSONObject resObj = (JSONObject) new JSONParser().parse(response);
				System.out.println("Generated O/P============>"+ resObj.get("output"));
				System.out.println("Hidden O/P============>"+ question.getHiddenOutput());
				if( question.getHiddenOutput().equals( resObj.get("output")) ||  question.getHiddenOutput().equals( "\n"+resObj.get("output"))) {
					obtainedMarks += Integer.parseInt(answer.get("marks").toString());
				} 
			} else {
				for (Options opt : question.getOptions()) {
					if (opt.getOptionsId() == Long.parseLong(answer.get("optionId") + "")) {
						Boolean userAnswer = opt.getCorrectAnswer();
						if (userAnswer == true) {
//							Boolean userCorrectAnswer = opt.getCorrectAnswer();
//							modelMap.addAttribute("userCorrectAnswer", userCorrectAnswer);
							obtainedMarks += Integer.parseInt(answer.get("marks").toString());
						} else {
//							Boolean userWrongAnswer = opt.getCorrectAnswer();
//							modelMap.addAttribute("userCorrectAnswer", userWrongAnswer);
						}
					}
				}
			}
			
		}

		// Below code returns whether user is failed or passed
		double passingCriteria = this.addTestService.getAddTestById(testId).getPassingPercent();
		double per = (obtainedMarks/totalMarks)*100;
		String result = null;
		if(per>=passingCriteria) result = "PASS";
		else result = "FAIL";

		// Saving TestResult
		testResult.setAttempted(attempted);
		testResult.setObtainedMarks(obtainedMarks);
		testResult.setResultStatus(result);
		testResult.setTestName(testName);
		testResult.setTotalMarks(totalMarks);
		testResult.setDate( Calendar.getInstance().getTime());
		this.testResultService.saveTestResult(testResult);

		return null;
	}

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
