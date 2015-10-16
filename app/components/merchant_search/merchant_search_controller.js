angular.module('womply').controller('MerchantSearchController',
  ['SearchQueryModel', '$timeout', '$mdDialog', '$http',
  function(SearchQueryModel, $timeout, $mdDialog, $http) {
    this.queryModel = new SearchQueryModel();
    this.queryResults = [];
    this.queryFacets = {};
    var self = this;

    this.clear = function() {
      self.queryModel = new SearchQueryModel();
      self.queryResults = [];
      self.queryFacets = {};
    };

    this.addFacet = function(key, value) {
      self.queryModel.updateFacet(key, value);
      self.updateResults();
    };

    this.openMenu = function($mdOpenMenu, ev) {
      $mdOpenMenu(ev);
    };

    this.showCloud = function(mid) {
      function ModalController($http,$mdDialog) {
        this.words;
        var self = this;
        $http.get('http://10.0.1.8:44338/v1/word-cloud/'+mid).then(function(res) {
          console.log(res.data);
          self.words = res.data;
        });
      };

      var cloudDialog = {
        locals: {mid:mid},
        clickOutsideToClose: true,
        template: '<md-dialog><md-dialog-content>{{modal.words}}</md-dialog-content></md-dialog>',
        controller: ModalController,
        controllerAs: 'modal'
      };

      $mdDialog.show(cloudDialog);
    };

    this.updateResults = function() {
      self.queryModel.executeQuery().then(function(res){
        $timeout(function() {
          self.queryResults = res.results;
           _.each(res.facets, function(obj) {
             self.queryFacets[obj.name] = [];
             _.each(obj.values, function(key, value) {
               self.queryFacets[obj.name].push({name:value, value:key});
             });
          });
        });
      });
    }
  }]);
