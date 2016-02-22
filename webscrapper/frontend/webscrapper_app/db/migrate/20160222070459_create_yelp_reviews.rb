class CreateYelpReviews < ActiveRecord::Migration
  def change
    create_table :yelp_reviews do |t|

      t.timestamps null: false
    end
  end
end
