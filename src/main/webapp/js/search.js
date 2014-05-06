

function getSearchFromButton(){
	
	
	var pageSelection=$("#page_no").val();
	
	var searchString = $("#Search_Input").val();
	
	var dataSourceArr=[];
		        
        $(':checkbox:checked').each(function(i){
        	dataSourceArr[i] = $(this).val();
        });
        
        if(dataSourceArr.length>0 && searchString=="" ){
        	/*alert("Please type search String");
        	return;*/
        	searchString="*:*";
        }
        
        if(dataSourceArr.length==0){        	
        	dataSourceArr=["Investigator" ,"OCG-Document" ,"SharePoint"];        	
        	
        	if(searchString==null || searchString==""){        		 		
        		searchString="*:*";
        	}else{
        		searchString = $("#Search_Input").val();
        	}
        }
        
        displaySearchData(searchString,dataSourceArr,pageSelection);   
}


function clearData(){
	var searchString = $("#Search_Input").val("");
	$("#search_details_div").empty();	
	$('input:checkbox[name=dataSourceArray[]]').attr('checked',false);
	
	
}

function displaySearchData(searchText,dataSourceArr,pageSelection){
	
	var searchsAndDataSourceArr=[];
		
	var initial_data=({	
		'searchText':searchText,
		'pageSelection':pageSelection,
		'dataSource':JSON.stringify(dataSourceArr)
	});
	
	
	getSearchIndexDataAjax("/Wynyard-POC/rest/index/searchsolr",initial_data,function(data){
		$("#search_details_div").empty();
		
		$("#docFoundId").html("");
		$("#respTimeId").html("");
		
		if(data.searchResult.length==0){
			
			$("#docFoundId").html("");
			$("#respTimeId").html("");
			alert("No Result found");			
			return;
		}
		
		if(data.searchResult.length>0){
			$("#docFoundId").html("<b style=\"font-size: 16\">Documents Found:"+data.docFound );
			$("#respTimeId").html("<b style=\"font-size: 16\">Response Time:"+data.respTime+" MilliSecond");
			
		}
		
		var searchData = jQuery.parseJSON(data);
		var result_detail_div = document.getElementById("search_details_div");
		
		
		var ulTag = document.createElement("ul");
		
    	for(var i=0;i<data.searchResult.length;i++){
    		
    		var liTag = document.createElement("li");
    		liTag.setAttribute("style","list-style-type: none");
    		
    		var innerDiv = document.createElement("div");
    		
    		var Divh2 = document.createElement("h2");
    		Divh2.setAttribute("style","color: blue")
    		var Divh3 = document.createElement("h3");	
    		

    		
    		if(data.searchResult[i].dataSource=="Investigator"){
    			
    			
    			var entityType_h4 = document.createElement("h3");
    			var superTypeNone_h5 = document.createElement("h3");
    			var oId_h6 = document.createElement("h3");
    			
    			var title = $.trim(data.searchResult[i].content.substring(0,40));
    			Divh2.innerHTML=title;
    			
    			
    			entityType_h4.innerHTML="EntityType:"+data.searchResult[i].entitytype;
    			superTypeNone_h5.innerHTML="SuperTypeNote:"+data.searchResult[i].supertypenote;
    			oId_h6.innerHTML="OId :"+data.searchResult[i].oid;
    			innerDiv.appendChild(Divh2);
    			innerDiv.appendChild(entityType_h4);
    			innerDiv.appendChild(superTypeNone_h5);
    			innerDiv.appendChild(oId_h6);
    			
    			Divh3.innerHTML="DataSource :"+data.searchResult[i].dataSource;
    			
    			
    			var textContent =data.searchResult[i].content			
    			
        		
    			if(textContent.length>500){
    				textContent=textContent.substring(0,500);
    				
    				textContent=textContent+"....";
    			}
    			
    			var textContent = document.createTextNode(textContent);
    			
        		
        		
        		innerDiv.appendChild(Divh3);
        		innerDiv.appendChild(textContent);
    			
    		}else if(data.searchResult[i].dataSource=="OCG"){
    			var textContent="";
    			
    			var entityTypeId = document.createElement("h3");
    			var county = document.createElement("h3");
    			var site = document.createElement("h3");
    			var sourceUrl = document.createElement("h3");
    			var name = document.createElement("h3");
    			var summary = document.createElement("h3");
    			
    			if(data.searchResult[i].EntityTypeId!=null){ // for OCG-Entity datasource
    				textContent=document.createTextNode(data.searchResult[i].name);
    				Divh2.innerHTML=data.searchResult[i].name;
    				innerDiv.appendChild(Divh2);
    				entityTypeId.innerHTML="EntityTypeId:"+data.searchResult[i].EntityTypeId;
    				
    				Divh3.innerHTML="DataSource :"+data.searchResult[i].dataSource;

            		innerDiv.appendChild(Divh3);
            		innerDiv.appendChild(entityTypeId);
            		innerDiv.appendChild(textContent);
    			}else{
    				Divh2.innerHTML=data.searchResult[i].title;
    				textContent = document.createTextNode(data.searchResult[i].summary);
    				county.innerHTML="Country:"+data.searchResult[i].country;
        			site.innerHTML="Site:"+data.searchResult[i].site;
        			name.innerHTML="Name :"+data.searchResult[i].name;
        			innerDiv.appendChild(Divh2);
        			innerDiv.appendChild(county);
        			innerDiv.appendChild(site);
        			innerDiv.appendChild(name);
        			innerDiv.appendChild(sourceUrl);
        			
        			Divh3.innerHTML="DataSource :"+data.searchResult[i].dataSource;

            		innerDiv.appendChild(Divh3);
            		innerDiv.appendChild(textContent);
    			}
    			
    		}else if(data.searchResult[i].dataSource=="SharePoint"){
    			
    			var fileName = document.createElement("h3");
    			var createdDate = document.createElement("h3");
    			var modifieldDate = document.createElement("h3");
    			    			
    			Divh2.innerHTML=data.searchResult[i].title;
    			fileName.innerHTML="File Name:"+data.searchResult[i].name;
    			createdDate.innerHTML="Created Date:"+data.searchResult[i].createddate;
    			modifieldDate.innerHTML="Modifield Date :"+data.searchResult[i].modifydate;
    			
    			innerDiv.appendChild(Divh2);
    			innerDiv.appendChild(fileName);
    			innerDiv.appendChild(createdDate);
    			innerDiv.appendChild(modifieldDate);
    			
    			Divh3.innerHTML="DataSource :"+data.searchResult[i].dataSource;
    		
    			var textContent =data.searchResult[i].content			
        		
    			if(textContent.length>500){
    				textContent=textContent.substring(0,500);
    				textContent=textContent+"......";
    			}
    			
    			var textContent = document.createTextNode(textContent);   			
    			
        		innerDiv.appendChild(Divh3);
        		innerDiv.appendChild(textContent);
    			   			
    		}    		    		
    		
    		ulTag.appendChild(liTag);
			liTag.appendChild(innerDiv);
			result_detail_div.appendChild(ulTag)
    	}
    	
    });
	
} 


function getSearchIndexDataAjax(url,initial_data,callback) {	
	$.ajax( {
		type : "GET",
		url : url,
		async : true,
		cache : false,
		data :initial_data, 
		success : function(data) {
			data = data;
			callback(data);
		}
	});	
	return;	
}