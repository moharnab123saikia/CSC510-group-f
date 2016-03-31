class CreateReviews < ActiveRecord::Migration
  def change
    create_table :reviews do |t|

      t.timestamps null: false
      t.string :res_id
      t.string :yelp_id
      t.string :ta_id
      t.string :fs_id
      t.string :name
      t.string :photo_url
      t.float :lat
      t.float :lon
    end
  end
end
