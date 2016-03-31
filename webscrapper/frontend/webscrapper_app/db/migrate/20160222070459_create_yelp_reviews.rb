class CreateYelpReviews < ActiveRecord::Migration
  def change
    create_table :yelp_reviews do |t|
    	t.string :yelp_id
    	t.float :rating
    	t.integer :scale
    	t.string :url
      t.integer :ratings_count
    	t.string :reviews
      t.string :neg_reviews
      	t.timestamps null: false
    end
  end
end
