class CreateUsages < ActiveRecord::Migration
  def change
    create_table :usages do |t|
      t.string :res_id
      t.integer :show_count
      t.integer :yelp_count
      t.integer :fs_count
      t.integer :ta_count
      t.integer :book_count
      t.timestamps null: false
    end
  end
end
