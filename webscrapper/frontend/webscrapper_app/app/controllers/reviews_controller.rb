class ReviewsController < ApplicationController
	def index
		@restaurants = Review.search(params[:search])
    end

    def show
    end
end
