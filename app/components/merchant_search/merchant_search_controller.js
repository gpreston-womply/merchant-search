angular.module('womply').controller('MerchantSearchController',
  ['MerchantSearchService',
  function(MerchantSearchService) {
    this.queryModel = MerchantSearchService.getQueryModel();

  }]);
