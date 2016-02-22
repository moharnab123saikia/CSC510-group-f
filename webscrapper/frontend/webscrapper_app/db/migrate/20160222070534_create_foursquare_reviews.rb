class CreateFoursquareReviews < ActiveRecord::Migration
  def change
    create_table :foursquare_reviews do |t|

      t.timestamps null: false
    end
  end
end
