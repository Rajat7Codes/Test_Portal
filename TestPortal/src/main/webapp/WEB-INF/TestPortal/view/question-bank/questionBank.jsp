<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Question Bank</title>
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="menu-position-side menu-side-left full-screen">
	<!-- onload="myToggle();" -->
	<div class="content-i">
		<div class="content-box">
			<div class="row">
				<div class="col-lg-12">
					<div class="element-wrapper">
						<div class="element-box">
							<form:form
								action="${pageContext.request.contextPath }/admin/question/bank/save"
								modelAttribute="questionBank" method="post"
								enctype="multipart/form-data">

								<form:hidden path="questionBankId" />
								<form:hidden path="filePath" />
								<form:hidden path="contentType" />
								<div style="display: none;" id="typeArray">${questionTypeJson}</div>
								<h6 class="element-header mb-5">Question Bank</h6>

								<div class="row">
									<c:if test="${edit == false }">
										<div class="col-sm-6">
											<div class="form-group">
												<label>Question Type</label>
												<form:select class="form-control" name="questionType"
													onchange="myToggle();" path="questionType"
													id="questionType" itemLabel="type" multiple="single"
													items="${questionTypeList }" itemValue="questionTypeId" />
												<form:errors path="questionType" />
											</div>
										</div>
									</c:if>
									<c:if test="${edit == true }">
										<div class="col-sm-6">
											<div class="form-group">
												<label>Question Type</label>
												<form:select class="form-control" name="questionType"
													onchange="myToggle();" path="questionType"
													id="questionType" itemLabel="type" multiple="single"
													items="${questionTypeList }" itemValue="questionTypeId" />
												<form:errors path="questionType" />
											</div>
										</div>
									</c:if>

									<div class="col-sm-6">
										<div class="form-group">
											<form:label path="marks"> Marks</form:label>
											<form:input path="marks" class="form-control"
												placeholder="Enter Marks" type="text" />
											<form:errors path="marks"></form:errors>
										</div>
									</div>
								</div>
								<div class="form-group" id="questionDiv">
									<form:label path="question" for=""> Question</form:label>
									<form:input path="question" class="form-control"
										placeholder="Enter a Question" type="text" />
									<form:errors path="question"></form:errors>
								</div>
								<%-- <c:if test="${imageTypeStatus == false }">
									<div class="row">
										<div class="col-sm-6" id="subDiv">
											<div class="form-group">
												<form:label path="subject">Subject</form:label>
												<form:input path="subject" class="form-control"
													placeholder="Enter subject" type="text" />
												<form:errors path="subject"></form:errors>
											</div>
										</div>
										
									</div>
								</c:if> --%>
								<%-- <c:if test="${imageTypeStatus == true }"> --%>
								<div class="row">
									<div class="col-sm-6" id="subDiv">
										<div class="form-group">
											<form:label path="subject">Subject</form:label>
											<form:input path="subject" class="form-control"
												placeholder="Enter subject" type="text" />
											<form:errors path="subject"></form:errors>
										</div>
									</div>
									<div class="col-sm-6" id="imageToggle">
										<div class="form-group">
											<label>Image Upload</label> <input
												class="form-control file-upload" type="file" id="imageName"
												name="imageName" />
											<form:errors path="imageName"></form:errors>
										</div>
									</div>
								</div>
								<%-- </c:if> --%>
								<div class="form-group">
									<form:label path="description"> Description</form:label>
									<form:textarea path="description" class="form-control" rows="3"></form:textarea>
									<form:errors path="description"></form:errors>
								</div>


								<c:if test="${edit == false }">
									<div id="optionsDiv">
										<h6 class="element-header mt-5">Options</h6>
										<div class="form-inline mt-5">
											<label> Options :</label><input
												class="form-control mb-2 mr-sm-2 mb-sm-0 col-sm-6 ml-5"
												placeholder="Option Name" id="optionName"><label
												class="form-check-label text-center col-sm-3 mb-2"><input
												class="form-check-input" type="checkbox" id="correct">
												Correct</label> <i class="btn btn-primary" id="add">Add</i>
										</div>


										<div class="mb-5">
											<div class="table-responsive mb-5">

												<table id="allDetailsTableBody"
													class="table table-striped table-lightfont">
													<thead>
														<tr>
															<th>Options</th>
															<th>Answer</th>
															<th>Action</th>
														</tr>
													</thead>
													<tbody id="optionsBody">

													</tbody>
												</table>
											</div>
										</div>
									</div>
								</c:if>
								<c:if test="${edit == true }">
									<div id="optionsDiv">
										<h6 class="element-header mt-5">Options</h6>
										<!-- <div class="form-inline mt-5">
											<label> Options :</label><input
												class="form-control mb-2 mr-sm-2 mb-sm-0 col-sm-6 ml-5"
												placeholder="Option Name" id="optionName"><label
												class="form-check-label text-center col-sm-3 mb-2"><input
												class="form-check-input" type="checkbox" id="correct">
												Correct</label> <i class="btn btn-primary" id="add">Add</i>
										</div> -->
										<div class="mb-5">
											<div class="table-responsive mb-5">

												<table id="optTableUpdate"
													class="table table-striped table-lightfont">
													<thead>
														<tr>
															<!-- <td>Question Bank Id</td> -->
															<td>Options Id</td>
															<td>Options</td>
															<td>Answer</td>

														</tr>
														<tr style="display: none;">
															<!-- <th>questionBankId</th> -->
															<th>optionsId</th>
															<th>optionName</th>
															<th>correctAnswer</th>

														</tr>
													</thead>
													<tbody id="optionsBodyForUpdate">
														<c:forEach var="options"
															items="${questionBank.getOptions() }" varStatus="ind">
															<tr>
																<%-- <td><input class="form-control"
																	value="${options.getQuestionBank().questionBankId}"
																	disabled="disabled"></td> --%>
																<td><input class="form-control"
																	value="${options.optionsId}" disabled="disabled"></td>
																<td><input class="form-control"
																	value="${options.optionName}"></td>
																<td><input class="form-control"
																	value="${options.correctAnswer }"></td>
															</tr>
														</c:forEach>

													</tbody>
												</table>
											</div>
										</div>
									</div>
								</c:if>
								<div class="form-buttons-w text-center">
									<input type="text" id="data" name="data" style="display: none;">
									<!-- <i class="btn" id="jsonBtn" onclick="addToUpdateJson1();">Add</i> -->
									<!-- <input type="button" onclick="newJsonUpdate();"> -->
									<c:if test="${edit == true }">
										<button class="btn btn-primary" type="submit"
											onclick="addToUpdateJson1();">Update</button>
									</c:if>
									<c:if test="${edit == false }">
										<button class="btn btn-primary" type="submit"
											onclick="newPageJson();">Submit</button>
									</c:if>
								</div>
							</form:form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
	function myToggle() {
		var type = document.getElementById("questionType").value;
		var typeArray = document.getElementById('typeArray').innerHTML;
		var parseJson = JSON.parse(typeArray);
		var stringifyJson = JSON.stringify(parseJson);
		//alert("stringifyJson ===>>>" + stringifyJson);
		for (i = 0; i <= parseJson.length; i++) {
			var obj = parseJson[i];
			var questionTypeId = obj.questionTypeId
			//var imageTypeId= obj.
			if (type == questionTypeId) {
				var questionType = obj.type;
				var programType = obj.programType;
				var imageType = obj.imageType;
				//alert("imageType ==>>" + imageType);
				if (imageType == true) {
					document.getElementById('subDiv').classList
							.remove("col-sm-12");
					document.getElementById('subDiv').classList.add("col-sm-6");
					document.getElementById('imageToggle').style.display = 'block';
					/* document.getElementById('questionDiv').style.display = 'none'; */
				} else {
					document.getElementById('imageToggle').style.display = 'none';

					document.getElementById('subDiv').classList
							.add("col-sm-12");
				}
				if (programType == true) {
					document.getElementById('optionsDiv').style.display = 'none';
				} else {
					document.getElementById('optionsDiv').style.display = 'block';
				}
			}
		}
	}
</script>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('#add')
								.click(
										function() {
											var option = document
													.getElementById("optionName").value;

											if ($("#correct").prop('checked') == true) {
												var answer = document
														.getElementById("optionName").value;
											} else {
												var answer = document
														.getElementById("optionName").value;
												answer = "";
											}
											var markup = "<tr><td>"
													+ option
													+ "</td><td>"
													+ answer
													+ "</td><td><i class='fa fa-trash' style='color:red;' onclick='deleteRow($(this));'><i></td>/tr>";

											$('#optionsBody').append(markup);
										});
					});
	function deleteRow(row) {
		row.closest('tr').remove();
	}
</script>
<script type="text/javascript">
	function newPageJson() {
		var sellEntryDetArray = [];
		var sellEntryTable = document.getElementById("optionsBody");

		alert("sellEntryTable===>" + sellEntryTable.rows.length);
		for (var i = 0; i < sellEntryTable.rows.length; i++) {
			//alert("sellEntryTable===>" + sellEntryTable.rows[i].cells[0].innerHTML);
			var jsonObject = {
				"optionName" : sellEntryTable.rows[i].cells[0].innerHTML,
				"correctAnswer" : sellEntryTable.rows[i].cells[1].innerHTML
			};
			//alert("json===>" + JSON.stringify(jsonObject));
			sellEntryDetArray.push(jsonObject);
		}
		var data = document.getElementById('data');
		data.value = JSON.stringify(sellEntryDetArray);
		//alert("json array===>" + JSON.stringify(sellEntryDetArray));
	}
</script>

<script type="text/javascript">
	var jsonArray = [];
	function addToUpdateJson1() {
		/* for each table row in table body */
		var tbl = $('#optTableUpdate tbody tr').map(
				function(idxRow, ele) {
					/* start building the retVal object */
					var retVal = {
						id : ++idxRow
					};
					/* for each cell */
					var $td = $(ele).find('td').map(
							function(idxCell, ele) {
								var input = $(ele).find(':input');
								/* if cell contains an input or select.... */
								if (input.length == 1) {
									var attr = $('#optTableUpdate thead tr th')
											.eq(idxCell).text();
									retVal[attr] = input.val();

								} else {
									var attr = $('#optTableUpdate thead tr th')
											.eq(idxCell).text();
									retVal[attr] = $(ele).text();
								}
							});
					return retVal;
				}).get();

		jsonArray.push(tbl);
		var data = document.getElementById("data");
		data.value = JSON.stringify(tbl);
		alert("update array data =====>>>" + JSON.stringify(tbl))
	}
</script>
</html>