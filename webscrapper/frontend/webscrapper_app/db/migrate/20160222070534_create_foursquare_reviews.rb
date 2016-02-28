class CreateFoursquareReviews < ActiveRecord::Migration
  def change
    create_table :foursquare_reviews do |t|
		t.string :fs_id
    	t.float :rating
    	t.integer :scale
    	t.string :url
    	t.string :reviews
      	t.timestamps null: false
      t.timestamps null: false
    end
  end
end
