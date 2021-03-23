<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.blackbook.webconsole.controllers.urls.Urls"%>
<!DOCTYPE html>
<html lang="en">
<head>
<link rel="stylesheet" type="text/css" href="/css/style.css">
<title>Shark EMM</title>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

<script src="https://apis.google.com/js/api.js"></script>
<link rel="manifest" href="/manifest.webmanifest">
</head>
<body>
	<div class="content">
		<div class="header">
			<h1>EMM Experience</h1>
			<p>Our demo web based console for EMM feature integration</p>
			<p>This is only a test!</p>
		</div>

		<div class="navbar">
			<a href="https://localhost:8443" class="active">Home</a>
			<!-- This console is mainly used for integration testing for the EMM Community and Partner support -->
		</div>

		<div class="row">

			<!-- side bar navigation -->

			<div class="side">
				<div class="sidebarcont">
					<!-- embed the sign up url here take this from the call -->
					<div class="enterpriseBtn" onClick="signup()"><a style="color: white">Enterprise
						Sign Up Flow</a></div>
				</div>
				<br> <br>
				<!-- Could we add a device profile and add the policies there and also work to obtain device info and also users enrolled via this device profile? -->
				<div class="sidebarcont">
					<div class="dropdown">
						<div class="dropdown-toggle" data-toggle="dropdown">
						<a style="color: white">
							Devices<span class="caret"></span>
							</a>
						</div>
						<ul class="dropdown-menu">
							<li><a style="color: black" onClick="devices()">Connected
									Devices</a></li>
							<li><a style="color: black"
								onClick="showqrcode()">QRCode</a></li>
							<li><a style="color: black"
								onClick="unenrollDevice()">Un-enroll All Devices</a></li>
						</ul>
					</div>
				</div>
				<br> <br>
				<div class="sidebarcont">
					<div class="dropdown">
						<div class="dropdown-toggle" data-toggle="dropdown">
							<a style="color: white" onClick="getCosuPolicy()">
								Policies<span class="caret"></span>
							</a>
						</div>
						<ul class="dropdown-menu">
							<li><a style="color: black"
								onClick="createIframe()">Managed Play iFrame</a></li>
							<!-- <li><a style="color: black"
								onClick="createManagedConfigIframe()">Managed Configurations iFrame</a></li>need to create -->
						</ul>
					</div>
				</div>
				<br> <br>
				<div class="sidebarcont">
					<!-- provides a list of users -->
					<div class="userBtn" onClick="listedUsers()"><a style="color: white">Get
						Users</a></div>
				</div>
			</div>


			<!-- main container -->
			<div class="main" id="container">
				<div class="flex-container">
				<div class="main_content">
					<h4>Not a real environment - this is only a test!</h4>
					<p>This is only for demonstrative purposes. Not a real environment!
					</p>
				</div>

				
			</div>

				<!-- Modal for Application Policies -->
				<div class="modal fade" id="myApplicationPolicyModal" role="dialog">
					<div class="modal-dialog">
						
								<!-- Modal content Content Form -->
								<div class="modal-content">
									<div class="modal-header">
										<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
										<h4 class="modal-title">Application Policies</h4>
									</div>
									
								
									
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_APPLICATION_POLICIES}"
											id="applicationsForm">
											
<!-- new way of application object input does allow multiple objects from the UI perspective -->

											
											<div class="form-group">
												<label for="configurations">Applications:</label>
												<hr>
													<table class="table">
														<thead>
															<tr>
																<th>PackageName</th>
																<th>InstallType</th>
																<th>Default</th>
															</tr>
														</thead>
														<tbody id="applicationPolicyTable">
															<c:forEach items="${applicationPolicies}" var="applicationPolicy">
																<tr class="apRow" applicationPolicyId = "${applicationPolicy.getId()}">
																		<td><input type="text" class="form-control"
																		id="ap-${applicationPolicy.getPackageName()}-packageName" name="ap-${applicationPolicy.getPackageName()}-packageName"
																		value="${applicationPolicy.getPackageName()}" ></td>
																		<td><input type="text" class="form-control"
																		id="ap-${applicationPolicy.getInstallType()}-installType" name="ap-${applicationPolicy.getInstallType()}-installType"
																		value="${applicationPolicy.getInstallType()}"></td>
																		<td><input type="text" class="form-control"
																		id="ap-${applicationPolicy.getDefaultPermissionPolicy()}-defaultPermissionPolicy" name="ap-${applicationPolicy.getDefaultPermissionPolicy()}-defaultPermissionPolicy"
																		value="${applicationPolicy.getDefaultPermissionPolicy()}"></td>
																		<td>
																		<!-- <button type="button" class="deletebtn" onclick = "uninstall()" title="Remove row">Uninstall</button> -->
																		</td>
																</tr>
															</c:forEach>
																<tr id="ap-new-row">
																		<td><input type="text" class="form-control"
																		id="newPackageName" name="ap-newPackageName"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newInstallType" name="ap-newInstallType"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newDefaultPermissionPolicy" name="ap-newDefaultPermissionPolicy"
																		value=""></td>
																		<td>
																		<!-- <button type="button" class="deletebtn" onclick = "uninstall()" title="Remove row">Uninstall</button> -->
																		</td>
																</tr>
														</tbody>
													</table>
													<button id="addMore">Add another app</button>
											</div>
											
											<br><br>
											
											<!-- Need to finish this!-->
											<div class="form-group">
												<label for="configurations">Selected Applications:</label>
												<hr>
													<table class="table table-striped">
														<thead>
															<tr>
																<th>PackageName</th>
																<th>Remove Application</th><!-- Might just need an onclick method to call applications.delete? -->
															</tr>
														</thead>
														<tbody id="selectedAppsTable">
																<c:forEach items="${selectedApps}">
																<%-- <tr class="apRow" value = "${applicationPolicy.getId()}"> --%>
																<tr class="apRow">
																		<td><input type="text" class="form-control"
																		id="ap-${applicationPolicy.getPackageName()}-packageName" name="ap-${applicationPolicy.getPackageName()}-packageName"
																		value="${applicationPolicy.getPackageName()}" ></td>
																		<%-- <td><input type="text" class="form-control"
																		id="ap-${applicationPolicy.getManagedConfigurationMap()}-installType" name="ap-${applicationPolicy.getManagedConfigurationMap()}-managedConfiguration"
																		value="${applicationPolicy.getManagedConfigurationMap()}"></td> --%>
																		<td><button onclick = "$(this).parent().parent().remove()" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>
																</tr>
															</c:forEach>
														</tbody>
													</table>
											</div>
												<br> <br>


											<!-- Managed Configuration input -->
											
											<%-- <div class="form-group">
												<label for="configurations">ManagedConfigurations:</label>
													<hr>
													<table class="table table-striped table-hover">
														<thead>
															<tr>
																<th>Attribute</th>
																<th>Value</th>
															</tr>
														</thead>
														<tbody id="managedPropertyTable">
														<c:forEach
															items="${managedConfigurationMap}"
															var="managed">
															<tr>
															<td><input type="text" class="form-control vk-key"
																onKeyUp="updateValuePair(this)"
																value="${managed.key}"></td>
															<td><input type="text" class="form-control vk-key-value" vk-key=""
																value="${managed.value}" style = "width:80%">
																<button onclick = "$(this).parent().parent().remove()" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button>
															</td>
															</tr>
															</c:forEach>
															<tr id="new-row">
															<td><input type="text" class="form-control"
																id="new-key" name="new-key"
																value=""></td>
															<td><input type="text" class="form-control"
																id="new-value" name="new-value"
																value=""></td>
															</tr>
														</tbody>
													</table>
															<button id="add-row" onclick="addRow(event)" type = "button" class="btn btn-success">Add Row</button>
												</div> --%>
											
											<!-- localstorage -->
											<div class="form-group">
												<label for="configurations">ManagedConfigurations:</label>
												<hr>
													<div id = "managedConfigurationTable"></div>
											</div> 

											<hr>

											<!-- Disabled input -->
											<div class="form-group">
												<label for="disabledOrNot">Disabled:</label> <input
													type="text" class="form-control" id="disabled"
													name="disabled" value="${applicationPolicyDisabled}">
											</div>

											<!-- Minimum Version Code input -->
											<div class="form-group">
												<label for="minVersion">MinimumVersionCode:</label> <input
													type="number" class="form-control" id="minimumVersionCode"
													name="minimumVersionCode"
													value="${applicationPolicyMinimumVersionCode}">
											</div>

											<hr>

											<hr>
											<label for="scopeOfDelegation">DelegatedScope(s):</label>
											<hr>
											<br> <select name="delegatedScopes" id="delegatedScopes"
												multiple>
												${applicationPolicyDelegatedScopes}
												<!-- <option value="DELEGATED_SCOPE_UNSPECIFIED">DELEGATED_SCOPE_UNSPECIFIED</option>
												<option value="CERT_INSTALL">CERT_INSTALL</option>
												<option value="MANAGED_CONFIGURATIONS" selected>MANAGED_CONFIGURATIONS</option>
												<option value="BLOCK_UNINSTALL">BLOCK_UNINSTALL</option>
												<option value="PERMISSION_GRANT">PERMISSION_GRANT</option>
												<option value="PACKAGE_ACCESS">PACKAGE_ACCESS</option>
												<option value="ENABLE_SYSTEM_APP">ENABLE_SYSTEM_APP</option> -->
											</select> <br> <br>
											<hr>

											<br> <br>

											<!-- Managed Configuration Template input -->
											<div class="form-group">
												<label for="configurations">Managed Configuration Template:</label>
												<hr>
												<h4>Template ID : <input type="text" class="form-control vk-tempId"
																	id="templateId" name="managedConfigTemplateId"
																	value="${applicationPolicyTemplateId}" ></h4>
													<table class="table">
														
														<thead>
															<tr>
																<th>Configuration Key</th>
																<th>Configuration Value</th>
																<th>Remove</th>
															</tr>
														</thead>
														<tbody id="managedConfigTemplateTable">
															<c:forEach items="${applicationPolicyManagedConfigVariable}" var="configVariables">
																<tr class="apTemplateAndConfigRow">
																		<td><input type="text" class="form-control vk-key"
																		id="configurationVariables" name="configurationVariablesKey"
																		value="${configVariables.key}"></td>
																		<td><input type="text" class="form-control vk-val"
																		id="configurationVariables" name="configurationVariablesVal"
																		value="${configVariables.value}"></td>
																		<td><button onclick = "$(this).parent().parent().remove()" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>
																</tr>
															</c:forEach>
																<tr id="ap-config-new-row">
																		<!-- <td><input type="text" class="form-control"
																		id="newTemplateId" name="ap-newTemplateId"
																		value=""></td> -->
																		<td><input type="text" class="form-control"
																		id="newConfigurationVariablesKey" name="ap-newConfigurationVariablesKey"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newConfigurationVariablesVal" name="ap-newConfigurationVariablesVal"
																		value=""></td>
																		<td><button style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>
																</tr>
														</tbody>
													</table>
													<button id="addMoreConfigs">Add more configVariables!</button>
											</div>							


											<!-- Accessible Track Id input -->
											<div class="form-group">
											<label for="configurations">AccessibleTrackIds:</label>
												<hr>
												<div class="form-group">

											<!-- Currently not showing - I think it won't work because accessibleTrackIds expects a list and not a map -->
											<table class="table table-striped table-hover">
												<thead>
													<tr>
														<th>Track Id</th>
														<th>Track Alias</th>
													</tr>
												</thead>
												<tbody id="accessibleTrackIdsTable">
													<c:forEach
														items="${applicationPolicyAccessibleTrackIds}"
														var="tracks">
														<tr class="apRowTr" appTrackInfoId = "${tracks.getId()}">
															<td><input type="text" class="form-control"
																value="${tracks.getTrackId()}"></td>
															<td><input type="text" class="form-control"
																value="${tracks.getTrackAlias()}"></td>
														</tr>
													</c:forEach>
													<tr id="new-row-tr">
																	<td><input type="text" class="form-control"
																		id="new-key-tr" name="new-key-tr"
																		value=""></td>
																	<td><input type="text" class="form-control"
																		id="new-value-tr" name="new-value-tr"
																		value=""></td>
																</tr>
													
												</tbody>
											</table>

											<button id="add-row-tr" onclick="addTrackIdRow(event)" type = "button" class="btn btn-success">Add Row</button>
											
											<hr>
											</div>
											</div>

											<!-- Submit button for overall form -->
											
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
									</div>

									<!-- Close Button input -->
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>
								</div>
						

					</div>
				</div>


				<!-- Modal for Password Requirements -->
				<div class="modal fade" id="myPasswordModal" role="dialog">
					<div class="modal-dialog">


								<!-- Modal content Content Form -->
								<div class="modal-content">
									<div class="modal-header">
										<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
										<h4 class="modal-title">Password Policy</h4>
									</div>
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_PASSWORD_POLICY}" id="policyForm">
											
											<div class="form-group">
												<label for="passwordMinLength">passwordMinLength:</label> <input
													type="number" type = "reset" class="form-control" id="passwordMinLength"
													name="passwordMinLength"
													value="${passwordPolicy.getPasswordMinLength()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumLetters">passwordMinimumLetters:</label>
												<input type="number" class="form-control"
													id="passwordMinimumLetters" name="passwordMinimumLetters"
													value="${passwordPolicy.getPasswordMinimumLetters()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumLowerCase">passwordMinimumLowerCase:</label>
												<input type="number" class="form-control"
													id="passwordMinimumLowerCase"
													name="passwordMinimumLowerCase"
													value="${passwordPolicy.getPasswordMinimumLowerCase()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumNonLetter">passwordMinimumNonLetter:</label>
												<input type="number" class="form-control"
													value="${passwordPolicy.getPasswordMinimumLetters()}"
													id="passwordMinimumNonLetter"
													name="passwordMinimumNonLetter"
													value="${passwordPolicy.getPasswordMinimumNonLetter()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumNumeric">passwordMinimumNumeric:</label>
												<input type="number" class="form-control"
													id="passwordMinimumNumeric" name="passwordMinimumNumeric"
													value="${passwordPolicy.getPasswordMinimumNumeric()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumSymbols">passwordMinimumSymbols:</label>
												<input type="number" class="form-control"
													id="passwordMinimumSymbols" name="passwordMinimumSymbols"
													value="${passwordPolicy.getPasswordMinimumSymbols()}">
											</div>
											<div class="form-group">
												<label for="passwordMinimumUpperCase">passwordMinimumUpperCase:</label>
												<input type="number" class="form-control"
													id="passwordMinimumUpperCase"
													name="passwordMinimumUpperCase"
													value="${passwordPolicy.getPasswordMinimumUpperCase()}">
											</div>
											<div class="form-group">
												<label for="passwordQuality">passwordQuality:</label> <input
													type="text" class="form-control" id="passwordQuality"
													name="passwordQuality" list="passwordQualityList"
													value="${passwordPolicy.getPasswordQuality()}">
												<datalist id="passwordQualityList">
													<option value="PASSWORD_QUALITY_UNSPECIFIED">
													<option value="BIOMETRIC_WEAK">
													<option value="SOMETHING">
													<option value="NUMERIC">
													<option value="NUMERIC_COMPLEX">
													<option value="ALPHABETIC">
													<option value="ALPHANUMERIC">
													<option value="COMPLEX">
												</datalist>
											</div>
											<div class="form-group">
												<label for="passwordHistoryLength">passwordHistoryLength:</label>
												<input type="number" class="form-control"
													id="passwordHistoryLength" name="passwordHistoryLength"
													value="${passwordPolicy.getPasswordHistoryLength()}">
											</div>
											<div class="form-group">
												<label for="maximumFailedPasswordsForWipe">maximumFailedPasswordsForWipe:</label>
												<input type="number" class="form-control"
													id="maximumFailedPasswordsForWipe"
													name="maximumFailedPasswordsForWipe"
													value="${passwordPolicy.getMaximumFailedPasswordsForWipe()}">
											</div>
											<div class="form-group">
												<label for="passwordExpirationTimeout">passwordExpirationTimeout:</label>
												<input type="text" class="form-control"
													id="passwordExpirationTimeout"
													name="passwordExpirationTimeout"
													pattern="[0-9](\.[0-9]{1,3})?(s)"
													title="Duration format in seconds"
													value="${passwordPolicy.getPasswordExpirationTimeout()}">
											</div>
											<div class="form-group">
												<label for="passwordScope">passwordScope:</label> <input
													type="text" class="form-control" id="passwordScope"
													name="passwordScope"
													value="${passwordPolicy.getPasswordScope()}">
											</div>
											<div class="form-group">
												<label for="requirePasswordUnlock">requirePasswordUnlock:</label>
												<input type="text" class="form-control"
													id="requirePasswordUnlock" name="requirePasswordUnlock"
													value="${passwordPolicy.getRequirePasswordUnlock()}">
											</div>
										
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>
								</div>


					</div>
				</div>	


				<!-- Modal Content for System Update Requirements -->
				<div class="modal fade" id="mySystemUpdateModal" role="dialog">
					<div class="modal-dialog">
					
								<!-- Modal content Content form-->
								<div class="modal-content">
									<div class="modal-header">
										<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
										<h4 class="modal-title">System Update Policy</h4>
									</div>
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_SYSTEM_UPDATE}"
											id="systemUpdateForm">
											<div class="form-group">
												<label for="sysType">System Update Type</label> <input
													type="text" class="form-control" id="systemUpdateType"
													placeholder="Enter System Update Type" name="type"
													list="systemUpdateQualityList"
													value="${systemUpdatePolicy.getType()}">
												<datalist id="systemUpdateQualityList">
													<option value="SYSTEM_UPDATE_TYPE_UNSPECIFIED">
													<option value="AUTOMATIC">
													<option value="WINDOWED">
													<option value="POSTPONE">
												</datalist>
												<label for="start">Start Minutes</label> <input type="text"
													class="form-control" id="startMinutes"
													placeholder="Enter start minutes" name="startMin"
													value="${systemUpdatePolicy.getStartMin()}"> <label
													for="end">End Minutes</label> <input type="text"
													class="form-control" id="endMinutes"
													placeholder="Enter end minutes" name="endMin"
													value="${systemUpdatePolicy.getEndMin()}"> 
													<table class="table">
														<thead>
															<tr>
																<th>StartMonth</th>
																<th>StartDay</th>
																<th>EndMonth</th>
																<th>EndDay</th>
															</tr>
														</thead>
													<tbody id="freezePeriodPolicyTable">
													<c:forEach items="${freezePeriods}" var="freezePeriodPolicy">
														<tr class="fpRow" freezePeriodPolicyId = "${freezePeriods.getId()}">
																<td><input type="text" class="form-control"
																id="fp-${freezePeriods.getId()}-startMonth" name="fp-${freezePeriods.freezePeriods.getId()}-startMonth"
																value="${freezePeriods.getStartMonth()}" ></td>
																<td><input type="text" class="form-control"
																id="fp-${freezePeriods.getId()}-startDay" name="fp-${freezePeriods.getId()}-startDay"
																value="${freezePeriods.getStartDay()}"></td>
																<td><input type="text" class="form-control"
																id="fp-${freezePeriods.getId()}-endMonth" name="fp-${freezePeriods.getId()}-endMonth"
																value="${freezePeriods.getEndMonth()}"></td>
																<td>
																<td><input type="text" class="form-control"
																id="fp-${freezePeriods.getId()}-endDay" name="fp-${freezePeriods.getId()}-endDay"
																value="${freezePeriods.getEndDay()}"></td>
														</tr>
													</c:forEach>
													<tr id="fp-new-row">
																		<td><input type="text" class="form-control"
																		id="newStartMonth" name="fp-newStartMonth"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newStartDay" name="fp-newStartDay"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newEndMonth" name="fp-newEndMonth"
																		value=""></td>
																		<td><input type="text" class="form-control"
																		id="newEndDay" name="fp-newEndDay"
																		value=""></td>
																		<td>
																		<!-- <button type="button" class="deletebtn" onclick = "uninstall()" title="Remove row">Uninstall</button> -->
																		</td>
																</tr>
														</tbody>
														</table>
														<button id="addMoreFreezePeriods">Add another Freeze Period</button>
											</div>
											
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>

								</div>
								
							
					</div>
				</div>
				
				<!-- Modal Content for Policy Enforcement Rules -->
				<div class="modal fade" id="myPolicyEnforcementRulesModal"
					role="dialog">
					<div class="modal-dialog">
						
								<!-- Modal content Content form-->
								<div class="modal-content">
									<div class="modal-header">
										<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
										<h4 class="modal-title">Policy Enforcement Rules Policy</h4>
									</div>
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_POLICY_ENFORCEMENT_RULES}"
											id="policyEnforcementRuleForm">
											<div class="form-group">
												<label for="settings">Setting Name</label> <input
													type="text" class="form-control"
													id="policyEnforcementSettingName"
													placeholder="Enter Setting Name" name="settingName"
													value="${policyEnforcementRulesPolicy.getSettingName()}">
												<label for="blockAction">Block After Days</label> <input
													type="text" class="form-control"
													id="policyEnforcementBlockAfterDays"
													placeholder="Enter Integer for Block After Days"
													name="blockAfterDays"
													value="${policyEnforcementRulesPolicy.getBlockAfterDays()}">
												<label for="blockScope">Block Scope</label> <input
													type="text" class="form-control"
													id="policyEnforcementBlockScope"
													placeholder="Enter scope"
													name="blockScope"
													value="${policyEnforcementRulesPolicy.getBlockScope()}">
												<label for="wipeAction">Wipe After Days</label> <input
													type="text" class="form-control"
													id="policyEnforcementWipeAfterDays"
													placeholder="Enter Integer for Wipe After Days"
													name="wipeAfterDays"
													value="${policyEnforcementRulesPolicy.getWipeAfterDays()}">
												<label for="factoryResetProtection">PreserveFRP</label> <input
													type="text" class="form-control"
													id="policyEnforcementPreserveFrp"
													placeholder="Enter true or false to preserve FRP"
													name="preserveFrp"
													value="${policyEnforcementRulesPolicy.getPreserveFrp()}">
											</div>
											
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>

								</div>
								
							
					</div>
				</div>
				
				<!-- Modal Content for App Auto Update Policy -->
				<div class="modal fade" id="myAppAutoUpdateModal" role="dialog">
					<div class="modal-dialog">

								<!-- Modal content Content form-->
								<div class="modal-content">
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_APP_AUTO_UPDATE_POLICY}"
											id="appUpdateForm">
											<div class="form-group">
												<label for="appUpdate">App Auto Update Policy</label>
												<input class="form-control" id="appAutoUpdatePolicy"
													name="appAutoUpdatePolicy" list="appAutoUpdateDropDownList"
													value="${appAutoUpdatePolicy.getAppAutoUpdatePolicy()}"> 
												<datalist id="appAutoUpdateDropDownList">
													<option value="APP_AUTO_UPDATE_POLICY_UNSPECIFIED">
													<option value="CHOICE_TO_THE_USER">
													<option value="NEVER">
													<option value="WIFI_ONLY">
													<option value="ALWAYS">
												</datalist>
											</div>
											
											<button type="submit" class="btn btn-default">Submit</button>
										</form>

									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>

								</div>
								
						
					</div>
				</div>

				<!-- Modal Content for Untrusted Apps Policy -->
				<div class="modal fade" id="myAdvancedSecurityOverrideModal" role="dialog">
					<div class="modal-dialog">
						
								<!-- Modal content Content form-->
								<div class="modal-content">
									<div class="modal-header">
										<!-- <button type="button" class="close" data-dismiss="modal">&times;</button> -->
										<h4 class="modal-title">Advanced Security Overrides Policy</h4>
									</div>
									<div class="modal-body">
										<form method="POST" action="${Urls.ADD_UNTRUSTED_APPS}"
											id="advancedSecurityForm">
											
											<div class="form-group">
												<label for="debugging">USB Debugging</label> <input
													type="text" class="form-control" id="debuggingFeaturesAllowed"
													placeholder="Select from dropdown"
													name="debuggingFeaturesAllowed" list="debuggingDropDown"
													value="${advancedSecurityOverridesPolicy.getDebuggingFeaturesAllowed()}">
												<datalist id="debuggingDropDown">
													<option value="true">
													<option value="false">													
												</datalist>
											</div>
											
											<div class="form-group">
												<label for="unknownSources">Unknown Sources</label> <input
													type="text" class="form-control" id="unknownSourceDrop"
													placeholder="Select from drop down"
													name="untrustedAppsPolicy" list="unknownSourceList"
													value="${advancedSecurityOverridesPolicy.getUntrustedAppsPolicy()}">
												<datalist id="unknownSourceList">
													<option value="UNTRUSTED_APPS_POLICY_UNSPECIFIED">
													<option value="DISALLOW_INSTALL">
													<option value="ALLOW_INSTALL_IN_PERSONAL_PROFILE_ONLY">
													<option value="ALLOW_INSTALL_DEVICE_WIDE">
												</datalist>
											</div>
											
											<div class="form-group">
												<label for="safeBoot">Safe Boot</label> <input
													type="text" class="form-control" id="safeBootDisabled"
													placeholder="Select from dropdown"
													name="safeBootDisabled" list="safeBootList"
													value="${advancedSecurityOverridesPolicy.getSafeBootDisabled()}">
												<datalist id="safeBootList">
													<option value="true">
													<option value="false">
												</datalist>
											</div>
											
											<button type="submit" class="btn btn-default">Submit</button>
										</form>
									</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>

								</div>
							
							
					</div>
				</div>
				
				<!-- Modal Content for Permission Grants -->
					<div class= "modal fade" id="myPermissionModal" role="dialog">
							<div class="modal-dialog">
								
								<!-- Modal content Content form-->
								<div class="modal-content">
									<div class="modal-header">
									<h4 class="modal-title">System level Permission Grants</h4>
								</div>
								<div class="modal-body">
								<form method="POST" action="${Urls.ADD_PERMISSIONS}" id="permissionForm">
									<div class="form-group">
										<label for="grantedPermissionForApp">PermissionGrants:</label>
											<hr>
											<br> <label for="appPermissionGrantsPerm">Permission</label>
											<input type="text" class="form-control" id="permission" name="permission" value= "${permissionPolicy.getPermission()}"> <br>
										<label for="appPermissionGrants">Grant State</label> 
										<input type="text" class="form-control" id="policy" name="policy" list="appPermissionGrantsList" value= "${permissionPolicy.getPolicy()}">
											<datalist id="appPermissionGrantsList">
												<option value="PERMISSION_POLICY_UNSPECIFIED">
												<option value="PROMPT">
												<option value="GRANT">
												<option value="DENY">
											</datalist>
									</div>
									<button type="submit" class="btn btn-default">Submit</button>
								</form>
								</div>
									<div class="modal-footer">
										<button type="button" class="btn btn-default"
											data-dismiss="modal">Close</button>
									</div>
								</div>
							</div>
						</div>
			</div>
			<span class="glyphicon glyphicon-refresh appear" id="refreshBtn"
				onclick="refreshDevices()"
				style="float: right; display: none; margin-right: 200px"></span>
		</div>
	</div>

	<!-- footer -->

	<div class="footer">
		<h5>Created by kulumba@ Q1 - April 2020</h5>
	</div>
	
	<script src="/javascript/javascript.js">
		
	</script>

</body>
</html>
