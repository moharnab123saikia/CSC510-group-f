class CreateYelpReviews < ActiveRecord::Migration
  def change
    create_table :yelp_reviews do |t|
    	t.string :yelp_id
    	t.float :rating
    	t.integer :scale
    	t.string :url
    	t.string :reviews
      	t.timestamps null: false
    end
  end
end
