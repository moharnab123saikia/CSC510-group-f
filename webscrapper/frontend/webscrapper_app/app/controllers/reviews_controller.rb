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

        yelp_reviews = JSON.parse(("{\"reviews\":" + @yelp_review.reviews + "}" ).gsub("=>", ":"))
        ta_reviews = JSON.parse(("{\"reviews\":" + @ta_review.reviews + "}").gsub("=>", ":"))
        fs_reviews = JSON.parse(("{\"reviews\":" + @fs_review.reviews + "}").gsub("=>", ":"))

        @yelp_review_text = []
        @yelp_review_rating = []
        for i in 0..yelp_reviews['reviews'].count - 1
            @yelp_review_text[i] = yelp_reviews['reviews'][i]['review']
            @yelp_review_rating[i] = yelp_reviews['reviews'][i]['scale']
        end

        @ta_review_text = []
        @ta_review_rating = []
        for i in 0..ta_reviews['reviews'].count - 1
            @ta_review_text[i] = ta_reviews['reviews'][i]['review']
            @ta_review_rating[i] = ta_reviews['reviews'][i]['scale']
        end

        @fs_review_text = []
        @fs_review_rating = []
        for i in 0..fs_reviews['reviews'].count - 1
            @fs_review_text[i] = fs_reviews['reviews'][i]['review']
            @fs_review_rating[i] = fs_reviews['reviews'][i]['scale']
        end

    end
    def set_review

    end
end
