class CreateTripadvisorReviews < ActiveRecord::Migration
  def change
    create_table :tripadvisor_reviews do |t|

      t.timestamps null: false
    end
  end
end
