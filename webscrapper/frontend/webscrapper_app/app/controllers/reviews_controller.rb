class ReviewsController < ApplicationController
	before_action :set_review
	def index
		@restaurants = Review.search(params[:search])
    end

    def show
    	@review = Review.find(params[:id])
    	@yelp_id = @review.yelp_id
    	@ta_id = @review.ta_id
    	@fs_id = @review.fs_id
    	@yelp_review = YelpReview.find_by_yelp_id(@yelp_id)
    	@ta_review = TripadvisorReview.find_by_ta_id(@ta_id)
    	@fs_review = FoursquareReview.find_by_fs_id(@fs_id)
    	@aggregate = ( @yelp_review.rating*@yelp_review.ratings_count +
                     @ta_review.rating*@ta_review.ratings_count + 
                     ( @fs_review.rating / 2 )*@fs_review.ratings_count )/ (
                     @yelp_review.ratings_count + @ta_review.ratings_count +
                     @fs_review.ratings_count)
    	@aggregate = @aggregate.round(1)

    end
    def set_review

    end
end
