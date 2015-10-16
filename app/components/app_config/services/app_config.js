angular.module('womply')
  .factory('AppConfig', ['$q', '$location', 'Context', 'Environment', function($q, $location, Context, Environment) {
    var defer = $q.defer();

    var slug = Context.getCurrentMerchantSlug();
    defer.resolve({
      ApplicationId: 'insights',
      UserMenuLinks: [
        {
          text: 'Logout',
          href: Environment.getInsightsPath() + '/users/sign_out'
        }
      ],
      ApiBase: 'http://local.womply.com:3000',
      ApiPath: '/api/0.1',
      NavigationLinks: [
        {
          id:     'merchant_search',
          text:   'Search',
          route:  'merchant_search',
          active: true
        },
      ],
      NavigationSelected: function() {
        var self = this;
        var slug = Context.getCurrentMerchantSlug();

        $location.path('/' + slug + '/' + self.route)
      }
    });

    return defer.promise;
  }]);
