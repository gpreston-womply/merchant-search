angular.module('womply').service('MerchantSearchService',
  ['$http', 'SearchQueryModel',
  function($http, SearchQueryModel) {

  return {
    getQueryModel: function() {
      return new SearchQueryModel();
    }
  }
}]);
