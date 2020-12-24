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
	
	

$(".vk-key").each(function(){// makes sure that we have these values
									// submitted in real time to prepare for
									// onKeyUp
	 updateValuePair(this);
});

function signup(){
	    $.get("/enterpriseSignUpFlo", function(data){
	      alert("Click ok to complete the sign up flow at the following URL: \n" + data.signUpUrl);
	      loadUrl(data.signUpUrl);
	      modal.style.display = "none";
	      }
	    ); 
}
	
function loadUrl(){
	$.get("/enterpriseSignUpFlo", function(data){
		$("#refreshBtn").css("display", "none");
		window.location.href = data.signUpUrl;
	})
}

/* End Enterprise creation and sign up scripts */
 

/* Starts the policy scripts */

 function getCosuPolicy(){ // needs to always point back to the policies tab on
							// the menu or create a function that does so
	$.get("/getCosuPolicy", function(data) { 
		$(".main_content").html(`<p>${policyList}</p>`);
		var html = "";
			html += `<p>${policyList}</p>`;
		if(!html){
			html+="nothing here";
			isDataPresent = false;
		}
		$(".main_content").html(html);
	});
}
 
 
 function openModal(form_type){
	 switch(form_type){
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
		 break;
	 case "app-update":
		 $("#myAppAutoUpdateModal").modal("show");
		 break;
	 }
	 
 }

 $("#policyForm, #permissionForm, #systemUpdateForm, #policyEnforcementRuleForm, #advancedSecurityForm, #appUpdateForm").submit(function(event){
		event.preventDefault();
		var post_url = $(this).attr("action"); 
		var unindexed_array = $(this).serializeArray();
		var indexed_array = {};

		$.map(unindexed_array, function(n, i){
			indexed_array[n['name']] = n['value'];
		});										// submission
		
		$.ajax({url: post_url, contentType: 'application/json', dataType: 'json', data: JSON.stringify(indexed_array),
			type: 'POST',
			success: function(data, textStatus, jqXHR){
					window.location.assign("https://localhost:8443");	
				},
			error: function(XMLHttpRequest, textStatus, errorThrown){
				alert("There was an error!");
				}             
		});
	});

 
 
 $("#applicationsForm").submit(function(event){
	 	event.preventDefault();
	 	var post_url = $(this).attr("action");
	 var indexed_array = {};
	 var managedConfigurationMap = {};// managedConfigurations object
	 var delegatedScopes = [];
	 var applications = [];
	 var accessibleTrackIds = [];
	 var unindexed_array = $(this).serializeArray();
	
	 $(".apRowTr").each(function(index){
		 		var appTrackInfoObj = {};
		 		var id = $(this).attr("appTrackInfoId");
		 			if(id != undefined){
			 			appTrackInfoObj.id = id;
		 			}
		 			$(this).find("input").each(function(index){
		 				switch(index){
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

	 $(".apRow").each(function(index){// Now we have every single value, one
										// row at a time. "this" obtains every
										// field
		 var applicationObj = {};
		 var id = $(this).attr("applicationpolicyid");
		 if(id != undefined){// in the instance that it is blank or
								// undefined, it won't set an undefined value
		 applicationObj.id = id;// Gives us the id number right now for values
								// that exist
		 }
		 $(this).find("input").each(function(index){
			switch(index){
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
											// applicationObj(s) each having
											// different values for packageName,
											// installType and
											// defaultPermissionPolicy
		 console.log("applications here are:" + applicationObj);
	 });
	 
	 $(".vk-key-value").each(function(index){// Now we have every single
												// value, one row at a time.
												// "this" obtains every field
		 var key = $(this).attr("vk-key");
		 var value = $(this).val();
		 managedConfigurationMap[[key]] = value;
		 
	 }); 
	 
	 
// goal being sent over to fix single entry bug
// var applicationPolicies = [{
// id:7,
// packageName:"youtube",
// disabled:true,
// managedConfigurations:{
// key1:value1,
// key2:Value2
// }
//			 
// },{
// id:8,
// packageName:"gmail",
// disabled:true,
// managedConfigurations:{
// key1:value1,
// key2:Value2
// }
//			 
// }];
	 
	 
	 $.map(unindexed_array, function(n, i){
		 
		 if(n['name'].startsWith("ap")){// old application policy values
			
		 }else if(n['name'] == "delegatedScopes"){
			 delegatedScopes.push(n['value']);
// }else if(n['name'] == "accessibleTrackIds"){
// accessibleTrackIds[n['name'].replace("vk", "")];
// accessibleTrackIds.push(n['value']); //nothing shows up not the text input
// fields, just the rows
		 }else{
			 for(var i = 0; i < applications.length; i++){
				 applications[i][n['name']] = n['value'];// modifies each obj
															// at a time and
															// gives it the val
			 }
		 }
	 
	    });

	 
	 for(var i = 0; i < applications.length; i++){
		 applications[i].managedConfigurationMap = managedConfigurationMap;
		 applications[i].delegatedScopes = delegatedScopes;
		 applications[i].accessibleTrackIds = accessibleTrackIds;
	 }
	 
	 
	 
	 
	 	$.ajax({url: post_url, contentType: 'application/json', dataType: 'json', data: JSON.stringify(applications), 
	 		type: 'POST', 
	 		success: function(data, textStatus, jqXHR){
	 			$("myModal").modal("hide");
	 			window.location.assign("https://localhost:8443");
	 		},
	 		error: function(XMLHttpRequest, textStatus, errorThrown){
	 			alert("received by ajax: " + JSON.stringify(errorThrown));
	 		}
	 	});
 });

/* Ends Policies scripts */

/* Starts Devices scripts */

function devices(){
	$.get("/devices", function(data) {
		$(".main_content").html(data.map(x => `<p>The devices listed under this enteprise are:  
		<span class = 'deviceListUrl'> ${x.name} </span><button class = 'btn btn-warning' onclick = 'wipeDevice("${x.name}")'>Wipe Device</button>
		<button class = 'btn btn-danger' onclick = 'lockDevice("${x.name}")'>Lock Device</button></p>`));
		$("#refreshBtn").css("display", "block");
	});
}

function getPolicies(){
	 $.get("/getMyPolicy", function(data){
		   $(".main_content").html(data.policies);
		  });
}

function refreshDevices(){
	$.get("/devices",(data) => {
		$(".main_content").html(data.map(x => "<p>The devices listed under this enteprise are:  " + x.name + "</p>"));
	});
}

function showqrcode(){
	$.get("/enrollmentToken",(data) => {
		var newEnrollmentBtn = '<button id="newEnrollmentBtn" onclick="generateNewEnrollmentToken()">Generate New Enrollment Token</button>';
		var image = $("<img>").attr("src", "https://localhost:8443/getqrcode").attr("id", "qrcodeimg");
		var parameters = `<ul class="parameters-list"><li><b>COMPONENT_NAME</b></li><li>com.google.android.apps.work.clouddpc/.receivers.CloudDeviceAdminReceiver</li><li><b>SIGNATURE_CHECKSUM</b></li><li>I5YvS0O5hXY46mb01BlRjq4oJJGs2kuUcHvVkAPEXlg</li><li><b>PACKAGE_DOWNLOAD_LOCATION</b></li><li>https://play.google.com/managed/downloadManagingApp?identifier=setup</li><li><b>PROVISIONING_ADMIN_EXTRAS_BUNDLE</b></li><li>${data}</li></ul>`;
		$(".main_content").html(image);
		$(".main_content").append(parameters).append(newEnrollmentBtn);
		$("#refreshBtn").css("display", "block");
	});
}



function generateNewEnrollmentToken(){
	$.get("/devices",(data) => {
		console.log("New enrollment token created: ");
		console.log(data);
	});
}

function getFormData($form){
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function(n, i){
        indexed_array[n['name']] = n['value'];
    });
    

    return indexed_array;
}

/* Ends Devices Scripts */

/* Starts Acct and App Management */

function createIframe(){
	$.get("/getWebToken", function(data){
		console.log("data here is " + data);
		gapi.load('gapi.iframes', function() {
		    var options = {
		      'url': 'https://play.google.com/work/embedded/search?token=' + data + '&mode=SELECT',
		      'where': document.getElementById('container'),
		      'attributes': { style: 'width: 950px; height:500px', scrolling: 'yes'}
		    }
		    var iframe = gapi.iframes.getContext().openChild(options);
		    iframe.register('onproductselect', function(event){
		    	console.log("event from SELECT is: " + JSON.stringify(event));
		    	let managedAppsArray = localStorage.getItem("managedApps") || [];//Does this exist inside the localStorage? If not start off with a blank array.
		    	managedAppsArray = typeof managedAppsArray === 'string' ? JSON.parse(managedAppsArray) : []; //If we have found it, it will be a string, if not a sting, it's just an array...if it's a string we convert if back into an object and then use it.
		    	//1. We take the package name into localStorage as stringified array
		    	//2. We ensure that when it's stored it's unique package names (values)

		    	if(event.action == "selected"){
		    	    managedAppsArray.push(event.packageName);
		    	    managedAppsArray = [... new Set(managedAppsArray)];//In case of duplicates, reset the array to be unique values only
		    		localStorage.setItem("managedApps", JSON.stringify(managedAppsArray));//put it back into a string (JSON) and then back into localStorage.
		    		populateTable(managedAppsArray);
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

function createManagedConfigIframe(){
	$.get("/getManagedConfigToken", function(data){
		gapi.load('gapi.iframes', function() {
		    var options = {
		      'url': 'https://play.google.com/managed/mcm?token=' + data + '&packageName=com.google.android.gm',
		      'where': document.getElementById('container'),
		      'attributes': { style: 'height:1000px', scrolling: 'yes'}
		    }

		    var iframe = gapi.iframes.getContext().openChild(options);
		    iframe.register('onconfigupdated', function(event) {
		    	  console.log(event);
		    	  alert("Configuration saved!");
		    	}, gapi.iframes.CROSS_ORIGIN_IFRAMES_FILTER);
		    $("main_content").html(iframe);
		  });	
	});
}

function unenrollDevice(){
	$(".deviceListUrl").each(function(){
		var url = $(this).text();// obtained all the values here
		wipeDevice(url);
	});
}

function wipeDevice(url){
	var urlArray = url.split("/");
	$.get(`/deleteDevice/${urlArray[1]}/${urlArray[3]}`, function(data){
		console.log(JSON.stringify(data));
	}).fail(function(){
		alert("there is an error!");
	});
}

function lockDevice(url){
	var urlArray = url.split("/");
	$.get(`/lockDevice/${urlArray[1]}/${urlArray[3]}`, 
			function(data){
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


function addRow(event){
	event.preventDefault();
	var currentKey = $("#new-key").val();	
	var currentValue = $("#new-value").val();
	if(currentKey == currentValue){
		alert("Key must be different than value");
		return;
	}else if(currentKey == ""){
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


function addTrackIdRow(event){
	event.preventDefault();
	var currentKey = $("#new-key-tr").val();	
	var currentValue = $("#new-value-tr").val();
	if(currentKey == currentValue){
		alert("Key must be different than value");
		return;
	}else if(currentKey == ""){
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


function updateValuePair(input){
	console.log(input);
	$(input).parent().parent().find("input").eq(1).attr("vk-key", input.value);
}

function getRemoveButton(packageName){
	return `<td><button onclick = "$(this).parent().parent().remove(); removeFromStorage('${packageName}')" style = "width:initial; height:80%" type="button" class="button btn-danger">X</button></td>`;
}


function populateTable(tableData){
	appDataRow = "";
	for (const element of tableData){
		appDataRow += `<tr><td>${element}</td><td>Managed Configuration</td>${getRemoveButton(element)}</tr>`;
	}
	
}

function initializeManagedApps(){
	let managedAppsArray = localStorage.getItem("managedApps") || [];
	managedAppsArray = typeof managedAppsArray === 'string' ? JSON.parse(managedAppsArray) : []; 
	populateTable(managedAppsArray);
}
initializeManagedApps();

function removeFromStorage(packageName){
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