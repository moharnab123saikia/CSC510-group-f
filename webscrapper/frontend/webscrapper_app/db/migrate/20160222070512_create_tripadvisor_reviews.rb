class CreateTripadvisorReviews < ActiveRecord::Migration
  def change
    create_table :tripadvisor_reviews do |t|
    	t.string :ta_id
    	t.float :rating
    	t.integer :scale
    	t.string :url
      t.integer :ratings_count
    	t.string :reviews
      t.timestamps null: false
      t.timestamps null: false
    end
  end
end
