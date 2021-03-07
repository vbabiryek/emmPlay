var appDataRow;
var policyList = `<div class="btn-group">
					<button onclick = "openModal('password')">Password Policies</button>
					<button onclick = "openModal('application')">Application Policies</button>
					<button onclick = "openModal('system-update')">System Update Policies</button>
					<button onclick = "openModal('policy-enforcement')">Policy Enforcement Policies</button>
					<button onclick = "openModal('permission')">Permission Policies</button>
					<button onclick = "openModal('advanced-security')">Advanced Security Overrides Policy</button>
					<button onclick = "openModal('app-update')">App Auto Update Policy</button>
				</div>`



$(".vk-key").each(function() {// makes sure that we have these values
	// submitted in real time to prepare for
	// onKeyUp
	updateValuePair(this);
});

function signup() {
	$.get("/enterpriseSignUpFlo", function(data) {
		alert("Click ok to complete the sign up flow at the following URL: \n" + data.signUpUrl);
		loadUrl(data.signUpUrl);
		modal.style.display = "none";
	}
	);
}

function loadUrl() {
	$.get("/enterpriseSignUpFlo", function(data) {
		$("#refreshBtn").css("display", "none");
		window.location.href = data.signUpUrl;
	})
}

/* End Enterprise creation and sign up scripts */


/* Starts the policy scripts */

function getCosuPolicy() { // needs to always point back to the policies tab on
	// the menu or create a function that does so
	$.get("/getCosuPolicy", function(data) {
		$(".main_content").html(`<p>${policyList}</p>`);
		var html = "";
		html += `<p>${policyList}</p>`;
		if (!html) {
			html += "nothing here";
			isDataPresent = false;
		}
		$(".main_content").html(html);
	});
}


function openModal(form_type) {
	switch (form_type) {
		case "permission":
			$("#myPermissionModal").modal("show");
			break;
		case "password":
			$("#myPasswordModal").modal("show");
			break;
		case "system-update":
			$("#mySystemUpdateModal").modal("show");
			break;
		case "policy-enforcement":
			$("#myPolicyEnforcementRulesModal").modal("show");
			break;
		case "advanced-security":
			$("#myAdvancedSecurityOverrideModal").modal("show");
			break;
		case "application":
			$("#myApplicationPolicyModal").modal("show");
			$("#selectedAppsTable").html(appDataRow);
			populateManagedConfigurationTable();
			break;
		case "app-update":
			$("#myAppAutoUpdateModal").modal("show");
			break;
	}

}

$("#policyForm, #permissionForm, #systemUpdateForm, #policyEnforcementRuleForm, #advancedSecurityForm, #appUpdateForm").submit(function(event) {
	event.preventDefault();
	var post_url = $(this).attr("action");
	var unindexed_array = $(this).serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});										// submission

	$.ajax({
		url: post_url, contentType: 'application/json', dataType: 'json', data: JSON.stringify(indexed_array),
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			window.location.assign("https://localhost:8443");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("There was an error!");
		}
	});
});



$("#applicationsForm").submit(function(event) {
	event.preventDefault();
	var post_url = $(this).attr("action");
	var indexed_array = {};
	var managedConfigurationMap = {};
	var delegatedScopes = [];
	var applications = [];
	var accessibleTrackIds = [];
	var unindexed_array = $(this).serializeArray();
	var templateId = $("#templateId").val();
	var configurationVariables = [];

	$(".apRowTr").each(function(index) {
		var appTrackInfoObj = {};
		var id = $(this).attr("appTrackInfoId");
		if (id != undefined) {
			appTrackInfoObj.id = id;
		}
		$(this).find("input").each(function(index) {
			switch (index) {
				case 0:// trackId
					appTrackInfoObj["trackId"] = $(this).val();
					break;
				case 1:// trackAlias
					appTrackInfoObj["trackAlias"] = $(this).val();
					break;
			}

		});
		accessibleTrackIds.push(appTrackInfoObj);
	});

	


	$(".apRow").each(function(index) {
		var applicationObj = {};
		var id = $(this).attr("applicationpolicyid");
		if (id != undefined) {
			applicationObj.id = id;
		}
		$(this).find("input").each(function(index) {
			switch (index) {
				case 0:// packageName
					applicationObj["packageName"] = $(this).val();
					break;
				case 1:// installType
					applicationObj["installType"] = $(this).val();
					break;
				case 2:// defaultPermissionPolicy
					applicationObj["defaultPermissionPolicy"] = $(this).val();
					break;
			}
		});
		applications.push(applicationObj);// creates an array of multiple
		
	});
	
	if(templateId !== undefined && templateId !== null){
		let templateIdPolicyObj = {"templateId" : templateId};
		templateIdPolicyObj.configurationVariables = {};
		//iterating through all config variables which are part of the singular templateId policy obj.
		
		$(".apTemplateAndConfigRow").each(function(index){
			var managedConfigTemplateObj = {};
			var configurationKey = $(this).find("input:first").val();
			var configurationValue = $(this).find("input:last").val();
			templateIdPolicyObj.configurationVariables[[configurationKey]] = configurationValue;
	});
		for(let appObj of applications){
			appObj.templatePolicy = templateIdPolicyObj;
		}
	}

	$(".vk-key-value").each(function(index) {// Now we have every single
		// value, one row at a time.
		// "this" obtains every field
		var key = $(this).attr("vk-key");
		var value = $(this).val();
		managedConfigurationMap[[key]] = value;

	});


	$.map(unindexed_array, function(n, i) {

		if (n['name'].startsWith("ap")) {// old application policy values

		} else if (n['name'] == "delegatedScopes") {
			delegatedScopes.push(n['value']);
			// }else if(n['name'] == "accessibleTrackIds"){
			// accessibleTrackIds[n['name'].replace("vk", "")];
			// accessibleTrackIds.push(n['value']);
			// fields, just the rows
		} else {
			for (var i = 0; i < applications.length; i++) {
				applications[i][n['name']] = n['value'];// modifies each obj
				// at a time and
				// gives it the val
			}
		}

	}); 


	for (var i = 0; i < applications.length; i++) {
		//applications[i].configurationVariables = configurationVariables;
		applications[i].delegatedScopes = delegatedScopes;
		//applications[i].templateId = templateId;
		applications[i].accessibleTrackIds = accessibleTrackIds;
	}




	$.ajax({
		url: post_url, contentType: 'application/json', dataType: 'json', data: JSON.stringify(applications),
		type: 'POST',
		success: function(data, textStatus, jqXHR) {
			$("myModal").modal("hide");
			window.location.assign("https://localhost:8443");
		},
		error: function(XMLHttpRequest, textStatus, errorThrown) {
			alert("received by ajax: " + JSON.stringify(errorThrown));
		}
	});
});

/* Ends Policies scripts */

/* Starts Devices scripts */

function devices() {
	$.get("/devices", function(data) {
		$(".main_content").html(data.map(x => `<p>The devices listed under this enteprise are:  
		<span class = 'deviceListUrl'> ${x.name} </span>
		<button class = 'btn btn-warning' onclick = 'wipeDevice("${x.name}")'>Wipe Device</button>
		<button class = 'btn btn-danger' onclick = 'relinquishOwnership("${x.name}")'>Relinquish Ownership</button>
		<button class = 'btn btn-danger' onclick = 'lockDevice("${x.name}")'>Lock Device</button></p>`));
		$("#refreshBtn").css("display", "block");
	});
}

function getPolicies() {
	$.get("/getMyPolicy", function(data) {
		$(".main_content").html(data.policies);
	});
}

function refreshDevices() {
	$.get("/devices", (data) => {
		$(".main_content").html(data.map(x => "<p>The devices listed under this enteprise are:  " + x.name + "</p>"));
	});
}

function showqrcode() {
	$.get("/enrollmentToken", (data) => {
		var newEnrollmentBtn = '<button id="newEnrollmentBtn" onclick="generateNewEnrollmentToken()">Generate New Enrollment Token</button>';
		var image = $("<img>").attr("src", "https://localhost:8443/getqrcode").attr("id", "qrcodeimg");
		var parameters = `<ul class="parameters-list"><li><b>COMPONENT_NAME</b></li><li>com.google.android.apps.work.clouddpc/.receivers.CloudDeviceAdminReceiver</li><li><b>SIGNATURE_CHECKSUM</b></li><li>I5YvS0O5hXY46mb01BlRjq4oJJGs2kuUcHvVkAPEXlg</li><li><b>PACKAGE_DOWNLOAD_LOCATION</b></li><li>https://play.google.com/managed/downloadManagingApp?identifier=setup</li><li><b>PROVISIONING_ADMIN_EXTRAS_BUNDLE</b></li><li>${data}</li></ul>`;
		$(".main_content").html(image);
		$(".main_content").append(parameters).append(newEnrollmentBtn);
		$("#refreshBtn").css("display", "block");
	});
}



function generateNewEnrollmentToken() {
	$.get("/devices", (data) => {
		console.log("New enrollment token created: ");
		console.log(data);
	});
}

function getFormData($form) {
	var unindexed_array = $form.serializeArray();
	var indexed_array = {};

	$.map(unindexed_array, function(n, i) {
		indexed_array[n['name']] = n['value'];
	});


	return indexed_array;
}

/* Ends Devices Scripts */

/* Starts Acct and App Management */

function createIframe() {
	$.get("/getWebToken", function(data) {
		console.log("data here is " + data);
		gapi.load('gapi.iframes', function() {
			var options = {
				'url': 'https://play.google.com/work/embedded/search?token=' + data + '&mode=SELECT',
				'where': document.getElementById('container'),
				'attributes': { style: 'width: 950px; height:500px', scrolling: 'yes' }
			}
			var iframe = gapi.iframes.getContext().openChild(options);
			iframe.register('onproductselect', function(event) {
				console.log("event from SELECT is: " + JSON.stringify(event));

				if (event.action == "selected") {

					let managedAppsArray = addToLocalStorageArray("managedApps", event.packageName);
					populateTable(managedAppsArray);

					createManagedConfigIframe(event.packageName);
				}

				var htmlString = `<tr id="selected-app-row">
										<td><input type="text" class="form-control"
												id="newPackageName" name="ap-newPackageName"
												value=""></td>
										<td><button onclick = "removeSelectedApp()">Remove Selected App</button></td>
								</tr>`;
			}, gapi.iframes.CROSS_ORIGIN_IFRAMES_FILTER);
			$(".main_content").html(iframe);
		});
	});
}

function createManagedConfigIframe(packageName) {
	$.get("/getManagedConfigToken", function(data) {
		console.log("data here is: ", JSON.stringify(data));
		gapi.load('gapi.iframes', function() {
			var options = {
				'url': 'https://play.google.com/managed/mcm?token=' + data + `&packageName=${packageName}`,
				'where': document.getElementById('container'),
				'attributes': { style: 'height:1000px', scrolling: 'yes' },
				'canDelete': 'TRUE'
			}

			var iframe = gapi.iframes.getContext().openChild(options);
			iframe.register('onconfigupdated', function(event) {
				console.log(event);
				addToLocalStorageArray("mcmId", event);
				alert("Configuration saved!");
			}, gapi.iframes.CROSS_ORIGIN_IFRAMES_FILTER);
			$("main_content").html(iframe);
		});
	});
}

function unenrollDevice() {
	$(".deviceListUrl").each(function() {
		var url = $(this).text();// obtained all the values here
		wipeDevice(url);
	});
}

function wipeDevice(url) {
	var urlArray = url.split("/");
	$.get(`/deleteDevice/${urlArray[1]}/${urlArray[3]}`, function(data) {
		console.log(JSON.stringify(data));
	}).fail(function() {
		alert("there is an error!");
	});
}

function lockDevice(url) {
	var urlArray = url.split("/");
	$.get(`/lockDevice/${urlArray[1]}/${urlArray[3]}`,
		function(data) {
			alert(`This device ${data.name} is now locked`);
			console.log(JSON.stringify(data));
		});
}

$("#addMore").click(function(e) {
	e.preventDefault();
	var currentPackageName = $("#newPackageName").val();
	var currentInstallType = $("#newInstallType").val();
	var currentDefaultPermissionPolicy = $("#newDefaultPermissionPolicy").val();

	$("#ap-new-row").before(`<tr class="apRow">
									<td><input type="text" class="form-control" 
											value="${currentPackageName}"></td>
									<td><input type="text" class="form-control"
											value="${currentInstallType}"></td>
									<td><input type="text" class="form-control"
											value="${currentDefaultPermissionPolicy}"></td>
											</tr>`);

	$("#newPackageName").val("");
	$("#newInstallType").val("");
	$("#newDefaultPermissionPolicy").val("");
});

$("#addMoreConfigs").click(function(e) {
	e.preventDefault();
	var managedConfigVarKey = $("#newConfigurationVariablesKey").val();
	var managedConfigVarVal = $("#newConfigurationVariablesVal").val();

	$("#ap-config-new-row").before(`<tr class="apTemplateAndConfigRow">
											<td><input type="text" class="form-control" value="${managedConfigVarKey}"></td>
											<td><input type="text" class="form-control" value="${managedConfigVarVal}"></td>
											<td><button onclick = "$(this).parent().parent().remove()" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>
											</tr>`);

	$("#newConfigurationVariablesKey").val("");
	$("#newConfigurationVariablesVal").val("");

});

function makeTable(header, rowData) {
	var headerRow = "<thead><tr>";
	header = header.map(x => "<th>" + x + "</th>").join("").replace(",", "") + "</tr></thead>";
	headerRow += header;

	var tableBody = "<tbody>"; // 2D array
	rowData.forEach(row => { // now we're in the individual array
		row = row.map(x => "<td>" + x + "</td>");
		tableBody += `<tr>${row}</tr>`;//adds individual rows each time
	});
	tableBody += "</tbody>";//appends the tbody
	return `<table class="table table-striped"> ${headerRow} ${tableBody} </table>`;
}


function addRow(event) {
	event.preventDefault();
	var currentKey = $("#new-key").val();
	var currentValue = $("#new-value").val();
	if (currentKey == currentValue) {
		alert("Key must be different than value");
		return;
	} else if (currentKey == "") {
		alert("Current key is blank!");
		return;
	}
	$("#new-row").before(`<tr> 
							<td><input type="text" class="form-control 
								vk-key" onKeyUp="updateValuePair(this)"
								value="${currentKey}"></td>
							<td><input type="text" class="form-control 
								vk-key-value" vk-key="${currentKey}"
								value="${currentValue}">
								<button onclick = "$(this).parent().parent().remove()" 
								style = "width:initial; height:80%" type="button" class="button btn-danger">X</button>
								</td>
							</tr>`);

	$("#new-key").val("");
	$("#new-value").val("");

}


function addTrackIdRow(event) {
	event.preventDefault();
	var currentKey = $("#new-key-tr").val();
	var currentValue = $("#new-value-tr").val();
	if (currentKey == currentValue) {
		alert("Key must be different than value");
		return;
	} else if (currentKey == "") {
		alert("Current key is blank!");
		return;
	}
	$("#new-row-tr").before(`<tr class = "apRowTr"> 
							<td><input type="text" class="form-control 
								vk-key" onKeyUp="updateValuePair(this)"
								value="${currentKey}"></td>
							<td><input type="text" class="form-control 
								vk-key-value" vk-key="${currentKey}"
								value="${currentValue}">
								<button onclick = "$(this).parent().parent().remove()" 
								style = "width:initial; height:80%" type="button" class="button btn-danger">X</button>
								</td>
							</tr>`);

	$("#new-key-tr").val("");
	$("#new-value-tr").val("");

}


function updateValuePair(input) {
	console.log(input);
	$(input).parent().parent().find("input").eq(1).attr("vk-key", input.value);
}

function getRemoveButton(packageName) {
	return `<td><button onclick = "$(this).parent().parent().remove(); removeFromStorage('${packageName}')" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>`;
}


function populateTable(tableData) {
	appDataRow = "";
	for (const element of tableData) {
		appDataRow += `<tr><td>${element}</td>${getRemoveButton(element)}</tr>`;
	}

}

function initializeManagedApps() {
	let managedAppsArray = localStorage.getItem("managedApps") || [];
	managedAppsArray = typeof managedAppsArray === 'string' ? JSON.parse(managedAppsArray) : [];
	populateTable(managedAppsArray);
}
initializeManagedApps();

function removeFromStorage(packageName) {
	let managedAppsArray = localStorage.getItem("managedApps") || [];
	managedAppsArray = typeof managedAppsArray === 'string' ? JSON.parse(managedAppsArray) : [];
	managedAppsArray = managedAppsArray.filter(x => x != packageName);
	localStorage.setItem("managedApps", JSON.stringify(managedAppsArray));
}


/* In case we ever want to explore this route

function uninstall(){
	$.get("/getSilentUninstall", function(){
		$(document).on('click', 'button.deletebtn', function () {
      	$(this).closest('tr').remove();
      	return false;
   		});
	});
}
*/

function searchWords(htmlTag, searchWord) {//Searches for this htmltag and matching text
	$(htmlTag).each((index, element) => {
		if ($(element).text().search(searchWord) !== -1) { //persists the concept of strict equality - also gets the text of every span tag in the event I want to explore more for this route : )
			return true;
		}
	});
	return false;
}

function addToLocalStorageArray(keyName, value) {
	let localStorageArray = localStorage.getItem(keyName) || [];
	localStorageArray = typeof localStorageArray === 'string' ? JSON.parse(localStorageArray) : [];
	localStorageArray.push(value);
	localStorageArray = [... new Set(localStorageArray)];//removes duplicates
	localStorage.setItem(keyName, JSON.stringify(localStorageArray));
	return localStorageArray;

}

function populateManagedConfigurationTable() {
	if (localStorage.getItem("mcmId")) {
		let data = localStorage.getItem("mcmId") || [];
		data = typeof data === 'string' ? JSON.parse(data) : [];//because localStorage can only store Strings, this parses it from Strings into an object
		if (data.length !== 0) {
			let header = Object.keys(data[0]);
			let tableBody = [];
			data.forEach(x => tableBody.push(Object.values(x))); //x = temp objects of each array
			let table = makeTable(header, tableBody);
			$("#managedConfigurationTable").html(table);
		}

	}

}