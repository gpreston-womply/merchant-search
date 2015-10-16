angular.module('womply').factory('SearchQueryModel',
  ['MerchantSearchService',
  function(MerchantSearchService) {
    function SearchQueryModel() {
      var params = {
        body: null,
        location: null,
        'is_claimed': null,
        category: null,
        product: null,
        'yelp_is_claimed': null,
        'processor_name': null
      };
      var self = this;

      this.body = function(value) {
        return arguments.length ? (params.body = value) : params.body;
      };

      this.location = function(value) {
        return arguments.length ? (params.location = value) : params.location;
      };

      this.executeQuery = function() {
        return MerchantSearchService.query(self.toQueryString());
      };

      this.updateFacet = function(key, value) {
        params[key] = value;
      };

      this.toQueryString = function() {
        var qs = [];
        _.each(params, function(param, paramKey) {
          qs.push(paramKey+'='+param);
        });
        return '?' + qs.join('&');
      }
    };

    return SearchQueryModel;
  }]);
