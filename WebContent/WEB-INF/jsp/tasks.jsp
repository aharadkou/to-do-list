<%@page import="by.gsu.epamlab.constants.Constants"%>
<%@page import="by.gsu.epamlab.model.beans.Section"%>
<%@page import="by.gsu.epamlab.model.beans.Operation"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
<title>Tasks</title>
</head>
<jsp:include page="${Constants.JSP_HEADER}"/>
<body>
<div class="container">
	<div class="row">
		<div class="col-sm-6">
			<jsp:include page="${Constants.JSP_ERROR}"/>
			<c:if test="${section.addingPermitted}">
				<div>
		  			<button class="btn-lg btn btn-primary add-btn" data-toggle="collapse" 
		  				data-target="#addTask" aria-expanded="false" aria-controls="addTask">
		    		  Add task
		  			</button>		
	  			</div>							
				<div class="collapse" id="addTask">
					<div class="card card-body add-form">
						<form method="POST" enctype="multipart/form-data" 
							action="${pageContext.request.contextPath}${Constants.PATH_TASK_ADD}" >
							<input type="hidden" value="${section}"  name="${Constants.ATTR_SECTION}" />
							<div class="form-group">
									<label>Enter description:</label>
									<textarea required class="form-control" name="${Constants.ATTR_DESCR}"></textarea>			
							</div>
							<div class="form-group">
							    	<input type="file" name="${Constants.ATTR_FILE}" />					
							</div>
							<div class="form-group">
									<c:if test="<%=request.getAttribute(Constants.ATTR_SECTION) == Section.SOMEDAY%>">
										<label>Enter date:</label>
									</c:if>
							    	<input class="form-control" type="date" value="${section.getSectionDate()}" 
							    	       name="${Constants.ATTR_DATE}"
								    <c:choose> 
									    <c:when test="<%=request.getAttribute(Constants.ATTR_SECTION) != Section.SOMEDAY%>">
									    	style="display:none !important"
									    </c:when> 
									    <c:otherwise>
									    	min="${section.getSectionDate()}"
									    </c:otherwise>
								    </c:choose>
							        />
							</div>											    	
						<input class="btn btn-success" type="submit" value="Submit"  />
					  </form>	
				   </div>	
				</div>	
			</c:if>	
		</div>
	</div>	
	<div class="row">
		<div class="col-sm-12" >
			<table class="table table-responsive">
			<c:if test="${not empty listTasks}">
					<thead>		
						<tr>
							<th>
								<input id="checkAll" onClick="checkAll()" type="checkbox" />
							</th>
							<th>
								Description
							</th>
							<c:if test="<%=request.getAttribute(Constants.ATTR_SECTION) != Section.TODAY &&
										   request.getAttribute(Constants.ATTR_SECTION) != Section.TOMORROW
										%>"
							>
								<th>
									Date
								</th>
							</c:if>
							<th>
								File
							</th>
						</tr>
					</thead>
				</c:if>
				<tbody>
					<c:forEach items="${listTasks}" var="task" >
					<tr>
						<td>
							<input class="inputCheck" type="checkbox" name="${Constants.ATTR_CHECKED_TASKS}" 
							       value="${task.id}" />						
						</td>
						<td>
							<textarea disabled rows="3" cols="40">${task.description}</textarea>
						</td>
						<c:if test="<%=request.getAttribute(Constants.ATTR_SECTION) != Section.TODAY &&
									   request.getAttribute(Constants.ATTR_SECTION) != Section.TOMORROW
									%>"
						>
							<td>
						        <fmt:formatDate pattern = "${Constants.OUTPUT_DATE_PATTERN}" 
					        		 value = "${task.date}" />				
							</td>
						</c:if>												
						<td>
							<c:choose>
								<c:when test="${empty task.file && section.addingPermitted}">
									<form method="POST" enctype="multipart/form-data" 
										action="${pageContext.request.contextPath}${Constants.PATH_FILE_ADD}" >
										<input type="hidden" value="${section}"  name="${Constants.ATTR_SECTION}" />
										<input type="hidden" value="${task.id}" name="${Constants.ATTR_TASK_ID}"/>
										<label for="file-upload" class="btn btn-outline-primary">
										    Upload file
										</label>
										<input id="file-upload" type="file" name="${Constants.ATTR_FILE}" 
										    onchange="this.form.submit();"/>
									</form>
								</c:when>
								<c:when test="${not empty task.file && section.addingPermitted}">
									<form method="POST"	
									      action="${pageContext.request.contextPath}${Constants.PATH_FILE_DELETE}" >
										<input type="hidden" value="${section}"  name="${Constants.ATTR_SECTION}" />
										<input type="hidden" value="${task.id}" name="${Constants.ATTR_TASK_ID}"/>
										<input type="hidden" value="${task.file.getAbsolutePath() }" 
										       name="${Constants.ATTR_FILE}"/>
										<input class="btn btn-danger" type="submit" value="Delete file"
											   data-toggle="confirmation" 
											   data-title="Are you sure want to delete this file?"
											   data-popout="true"
										/>
									</form>		
								</c:when>						
							</c:choose>
							<c:if test="${not empty task.file}">
								<div>
									<form name="downloadForm" method="GET" 
										action="${pageContext.request.contextPath}${Constants.PATH_FILE_DOWNLOAD}">
										<input type="hidden" value="${section}"  name="${Constants.ATTR_SECTION}" />
										<input type="hidden" value="${task.file.getAbsolutePath() }" 
										       name="${Constants.ATTR_FILE}" />
									</form>
									<a href="#" onclick="submitDownload(this)">${task.file.getName()}</a>
									<span>(${task.getKBFileSize()} KB)</span>	
								</div>						
							</c:if>						
						</td>
					</tr>
					</c:forEach>
				</tbody>
			</table>
			<div>
			<c:if test="${not empty listTasks}">
				<c:forEach items="${section.operations}" var="operation">
					<form class="operation-form" method="POST" 
						  action="${pageContext.request.contextPath}${Constants.PATH_OPERATION}" 
						  onsubmit="submitOperationForm(this);return false;">
						<input type="hidden" value="${operation}" name="${Constants.ATTR_OPERATION}" />
						<input type="hidden" value="${section}" name="${Constants.ATTR_SECTION}" />
						
						<c:choose>
							<c:when test='<%=pageContext.findAttribute("operation") == Operation.DELETE%>'>
								<input class="btn btn-danger operation" type="submit" value="${operation.displayLabel}" 
									   data-toggle="confirmation" 
									   data-title="Are you sure want to delete selected tasks?"
									   data-popout="true"
								/>
								<input id="clearBin" class="btn btn-danger operation" type="button" value="Clear bin"
									   data-title="Are you sure want to clear bin?"
									   data-popout="true"
								/>																	
							</c:when>
							<c:otherwise>
								<input class="btn btn-primary operation" type="submit" value="${operation.displayLabel}" />
							</c:otherwise>
						</c:choose>
					</form>
				</c:forEach>
			</c:if>
			</div>				
		</div>
	</div>	
</div>
</body>
<jsp:include page="${Constants.JSP_FOOTER}"/>
</html>