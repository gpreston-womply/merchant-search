angular.module('womply').factory('SearchQueryModel', function() {
  function SearchQueryModel() {
    var body;
    var location;

    return {
      setBody: function(data) {
        body = data;
      },
      setLocation: function(data) {
        location = data;
      }
    }
  };

  return SearchQueryModel;
})
