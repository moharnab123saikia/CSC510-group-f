class Review < ActiveRecord::Base

	def self.search(search)
    if search
      search = search.downcase
      where("lower(name) like :search",search2: "#{search}",search: "%#{search}%")
    else
      all
    end
end
end
