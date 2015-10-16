angular.module('womply')
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider.
      when('/merchant_search', {
        templateUrl: 'html/components/merchant_search/merchant_search.html',
        controller: 'MerchantSearchController',
        controllerAs: 'search'
      })
  }]);
