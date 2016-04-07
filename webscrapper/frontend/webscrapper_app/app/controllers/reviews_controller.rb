class ReviewsController < ApplicationController
	before_action :set_review
	def index
		@restaurants = Review.search(params[:search]).page(params[:page]).per(4)
    end

    def show
    	@review = Review.find(params[:id])
        res_id = @review.res_id
        usage = Usage.find_by_res_id(res_id)
        show_count = usage.show_count
        show_count = show_count + 1
        usage.update(show_count: show_count)
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


        #negative reviews
        yelp_neg_reviews = JSON.parse(("{\"neg_reviews\":" + @yelp_review.neg_reviews + "}" ).gsub("=>", ":"))
        @yelp_neg_review_text = []
        @yelp_neg_review_rating = []
        for i in 0..yelp_neg_reviews['neg_reviews'].count - 1
            @yelp_neg_review_text[i] = yelp_neg_reviews['neg_reviews'][i]['review']
            @yelp_neg_review_rating[i] = yelp_neg_reviews['neg_reviews'][i]['scale']
        end

       @map = ['dummy']
       @hash = Gmaps4rails.build_markers(@map) do |user, marker|
           marker.lat @review.lat
           marker.lng @review.lon
       end




    end
    def set_review

    end

    def book
        @review = Review.find(params[:id])
        res_id = @review.res_id
        usage = Usage.find_by_res_id(res_id)
        book_count = usage.book_count
        book_count = book_count + 1
        usage.update(book_count: book_count)
        redirect_to @review.res_url
    end

    def yelp
        @review = Review.find(params[:id])
        res_id = @review.res_id
        usage = Usage.find_by_res_id(res_id)
        yelp_count = usage.yelp_count
        yelp_count = yelp_count + 1
        usage.update(yelp_count: yelp_count)
        @yelp_id = @review.yelp_id
        @yelp_review = YelpReview.find_by_yelp_id(@yelp_id)
        redirect_to @yelp_review.url

    end

    def fs
        @review = Review.find(params[:id])
        res_id = @review.res_id
        usage = Usage.find_by_res_id(res_id)
        fs_count = usage.fs_count
        fs_count = fs_count + 1
        usage.update(fs_count: fs_count)
        @fs_id = @review.fs_id
        @fs_review = FoursquareReview.find_by_fs_id(@fs_id)
        redirect_to @fs_review.url

    end

    def ta
        @review = Review.find(params[:id])
        res_id = @review.res_id
        usage = Usage.find_by_res_id(res_id)
        ta_count = usage.ta_count
        ta_count = ta_count + 1
        usage.update(ta_count: ta_count)
        @ta_id = @review.ta_id
        @ta_review = TripadvisorReview.find_by_ta_id(@ta_id)
        redirect_to @ta_review.url
        
    end
end
