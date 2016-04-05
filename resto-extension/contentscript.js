var link = window.location.href;
//sending request to get JSON data
function loadJSON(callback) {   

    var xobj = new XMLHttpRequest();
        xobj.overrideMimeType("application/json");
    xobj.open('GET', 'https://www.dropbox.com/s/2hgfywixpx6e2a5/restaurants.json?dl=1', true); // Replace 'my_data' with the path to your file
    xobj.onreadystatechange = function () {
          if (xobj.readyState == 4 && xobj.status == "200") {
            // Required use of an anonymous callback as .open will NOT return a value but simply returns undefined in asynchronous mode
            callback(xobj.responseText);
          }
    };
    xobj.send(null);  
 }

//parsing and displaying data to webpage
 function init() {
 loadJSON(function(response) {
  // Parse JSON string into object
    var actual_JSON = JSON.parse(response);
    var index;
    var yelp_rating, ta_rating, fs_rating;
    var yelp_link_found = 0;
    var ta_link_found = 0;
    var fs_link_found = 0;
    for(index in actual_JSON.restaurants) {
    	data = actual_JSON.restaurants[index];
    	for (res_key in data) {
    		var yelp_url = data[res_key]['data']['yelp']['url'];
    		var yelp_count = parseInt(data[res_key]['data']['yelp']['count']);
    		var ta_url = data[res_key]['data']['tripadvisor']['url'];
    		var ta_count = parseInt(data[res_key]['data']['tripadvisor']['count']);
    		var fs_url = data[res_key]['data']['foursquare']['url'];
    		var fs_count = parseInt(data[res_key]['data']['foursquare']['count']);
    		//calculate aggregate rating
    		var aggregate_rating;
    		console.log("Y: "+yelp_count+"T: "+ta_count+"F: "+fs_count);
    		yelp_rating = parseFloat(data[res_key]['data']['yelp']['rating']);
    		ta_rating = parseFloat(data[res_key]['data']['tripadvisor']['rating']);
    		fs_rating = parseFloat(data[res_key]['data']['foursquare']['rating']);
    		console.log("YR: "+yelp_rating+"TR: "+ta_rating+"FR: "+fs_rating);
    		var total_count = parseInt(ta_count) + parseInt(fs_count) + parseInt(yelp_count);
    		aggregate_rating = ((ta_rating*ta_count) + ((fs_rating/2)*fs_count) + (yelp_rating*yelp_count))/total_count;
    		aggregate_rating_precise = aggregate_rating.toFixed(1);

    		//detecting the type of link
    		console.log("Aggregate: "+aggregate_rating);
    		if (link.includes(yelp_url)||yelp_url.includes(link)&&Math.abs(yelp_url.length - link.length)<8) {
    			yelp_link_found = 1;
    		}
    		else if (link.includes(ta_url)||ta_url.includes(link)&&Math.abs(ta_url.length - link.length)<8) {
    			ta_link_found = 1;
    		}
    		else if (link.includes(fs_url)||fs_url.includes(link)&&Math.abs(fs_url.length - link.length)<8) {
    			fs_link_found = 1;
    		}
    	}
    	if (yelp_link_found == 1 || ta_link_found == 1|| fs_link_found == 1) {
    		break;
    	}
	 }

     //displaying the info on the extension
	 if (yelp_link_found == 1) {
	 	var display_text = '<div  id="topbar"><h3><a href ='+ ta_url+' >TripAdvisor</a> Rating: ' + ta_rating +"/5 ("+ta_count+" reviews)"+'\t|    \t<a href ='+ fs_url+' >FourSquare</a> Rating: '+ fs_rating +"/10 ("+fs_count+" reviews)"+'\t|    \tAggregate Rating: '+aggregate_rating_precise+'/5</h3></div >';
	 }
	 else if (ta_link_found == 1) {
	 	var display_text = '<div  id="topbar"><h3><h3><a href ='+ yelp_url+' >Yelp</a> Rating: ' + yelp_rating +"/5 ("+yelp_count+" reviews)"+'\t|    \t<a href ='+ fs_url+' >FourSquare</a> Rating: '+ fs_rating +"/10 ("+fs_count+" reviews)"+'\t|    \tAggregate Rating: '+aggregate_rating_precise+'/5</h3></div >';
	 }
	 else if (fs_link_found == 1) {
        $('#desktopHeader').css('position','relative');
	 	var display_text = '<div  id="topbar"><h3><h3><a href ='+ yelp_url+' >Yelp</a> Rating: ' + yelp_rating +"/5 ("+yelp_count+" reviews)"+'\t|    \t<a href ='+ ta_url+' >TripAdvisor</a> Rating: '+ ta_rating +"/5 ("+ta_count+" reviews)"+'\t|     \tAggregate Rating: '+aggregate_rating_precise+'/5</h3></div >';
	 }

     display_text += '<button class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" role="button"><span class="ui-button-text">Book Now</span></button>';
     $(document.body).prepend(display_text);


 });
}

init();
