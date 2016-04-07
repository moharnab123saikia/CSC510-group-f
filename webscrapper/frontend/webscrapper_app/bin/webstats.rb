#Run the below command in your rails app home 
#bundle exec rails runner "eval(File.read 'bin/webstats.rb')"

#restaurant_id, show_count, yelp_count, fs_count, ta_count, booking_count

require 'csv'

CSV.foreach(File.path("bin/input_webstats.csv"), 
	headers: true, 
	skip_blanks: true, 
	skip_lines: /^(?:,\s*)+$/) do |row|
	res_id = row[0]
	show_count = row[1]
	yelp_count = row[2]
	fs_count = row[3]
	ta_count = row[4]
	book_count = row[5]

	#save in db
	usage = Usage.new :res_id=>res_id, :show_count=>show_count, :yelp_count=>yelp_count, 
	              :fs_count=>fs_count, :ta_count=>ta_count, :book_count=>book_count
	usage.save
end



