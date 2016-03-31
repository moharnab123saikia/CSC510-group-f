class CreateFoursquareReviews < ActiveRecord::Migration
  def change
    create_table :foursquare_reviews do |t|
		t.string :fs_id
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
