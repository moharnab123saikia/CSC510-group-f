var link = window.location.href;
// var yelp_link = link.includes("yelp");
// var ta_link = link.includes("tripadvisor");
// var fs_link = link.includes("foursquare");

function loadJSON(callback) {   

    var xobj = new XMLHttpRequest();
        xobj.overrideMimeType("application/json");
    xobj.open('GET', 'https://s3.amazonaws.com/restoscrapper/restaurants.json', true); // Replace 'my_data' with the path to your file
    xobj.onreadystatechange = function () {
          if (xobj.readyState == 4 && xobj.status == "200") {
            // Required use of an anonymous callback as .open will NOT return a value but simply returns undefined in asynchronous mode
            callback(xobj.responseText);
          }
    };
    xobj.send(null);  
 }


 function init() {
 loadJSON(function(response) {
  // Parse JSON string into object
    var actual_JSON = JSON.parse(response);
    var index;
    var yelp_ratin, ta_rating, fs_rating;
    var yelp_link_found = 0;
    var ta_link_found = 0;
    var fs_link_found = 0;
    for(index in actual_JSON.restaurants) {
    	data = actual_JSON.restaurants[index];
    	for (res_key in data) {
    		var yelp_url = data[res_key]['data']['yelp']['url'];
    		var ta_url = data[res_key]['data']['tripadvisor']['url'];
    		var fs_url = data[res_key]['data']['foursquare']['url'];
    		if (link.includes(yelp_url)||yelp_url.includes(link)) {
    			yelp_link_found = 1;
    			ta_rating = data[res_key]['data']['tripadvisor']['rating'];
    			fs_rating = data[res_key]['data']['foursquare']['rating'];
    		}
    		else if (link.includes(ta_url)||ta_url.includes(link)) {
    			ta_link_found = 1;
    			yelp_rating = data[res_key]['data']['yelp']['rating'];
    			fs_rating = data[res_key]['data']['foursquare']['rating'];
    		}
    		else if (link.includes(fs_url)||fs_url.includes(link)) {
    			fs_link_found = 1;
    			ta_rating = data[res_key]['data']['tripadvisor']['rating'];
    			yelp_rating = data[res_key]['data']['yelp']['rating'];
    		}
    	}
    	if (yelp_link_found == 1 || ta_link_found == 1|| fs_link_found == 1) {
    		break;
    	}
	 }
	 if (yelp_link_found == 1) {
	 	alert("TripAdvisor Rating: " + ta_rating +"\n\nFourSquare Rating: "+fs_rating );
	 }
	 else if (ta_link_found == 1) {
	 	alert("Yelp Rating:  "+ yelp_rating+"\n\nFourSquare Rating: "+fs_rating);
	 }
	 else if (fs_link_found == 1) {
	 	alert("Yelp Rating:  "+yelp_rating+"\n\nTripAdvisor Rating: "+ta_rating);
	 }


 });
}

init();





// if(yelp_link)
//  alert("TripAdvisor Rating:  \n\nFourSquare Rating: ");
// else if(ta_link)
// 	alert("Yelp Rating:  \n\nFourSquare Rating: ");
// else if(fs_link)
// 	alert("Yelp Rating:  \n\nTripAdvisor Rating: ");

