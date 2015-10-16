angular.module('womply').service('MerchantSearchService',
  ['$http', '$q', 'Environment',
  function($http, $q, Environment) {

  return {
    query: function(query) {
      var def = $q.defer();
      var url = 'http://10.0.1.8:44338/v1/merchant-search' + query;
      $http.get(url).then(function(res) {
        console.log(url, res.data);
        def.resolve(res.data);
      });
      return def.promise;
    }
  }
}]);
