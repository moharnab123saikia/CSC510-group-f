class UsagesController < ApplicationController
	def index
		@res_names = []
		@res_ids = []
		@show_counts = []
		@yelp_counts = []
		@fs_counts = []
		@ta_counts = []
		@booking_counts = []

		count = 0
		Usage.all.each do |usage|
			@res_ids[count] = usage.res_id
			@show_counts[count] = usage.show_count
			@yelp_counts[count] = usage.yelp_count
			@fs_counts[count] = usage.fs_count
			@ta_counts[count] = usage.ta_count
			@booking_counts[count] = usage.book_count
			@res_names[count] = Review.find_by_res_id(usage.res_id).name
			count = count + 1
		end

		@total_count = count-1

		@total_show_count = 0
		@total_redirect_count = 0
		@total_booking_count = 0

		for i in 0..count-1
			@total_show_count = @total_show_count + @show_counts[i]
			@total_redirect_count = @total_redirect_count + @yelp_counts[i]
			@total_redirect_count = @total_redirect_count + @fs_counts[i]
			@total_redirect_count = @total_redirect_count + @ta_counts[i]
			@total_booking_count = @total_booking_count + @booking_counts[i]
		end

		@redirect_count_percentage = ((@total_redirect_count.to_f/@total_show_count.to_f)*100.0).round(2)
		@booking_count_percentage = ((@total_booking_count.to_f/@total_show_count.to_f)*100.0).round(2)
    end
end
