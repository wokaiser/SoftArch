App = Ember.Application.create();

//dummy data
var data = {
  id: '1',
  ownPlayground   : [['x','0','0','0'], ['0','0','0','0']],
  enemyPlayground : [['0','0','0','0'], ['0','0','x','0']],
  error           : undefined,
};

App.Router.map(function() {
  this.resource('about');
  this.resource('playground');
});

App.PlaygroundRoute = Ember.Route.extend({
  model: function() {
    return data;
  }
});

Ember.Handlebars.helper('pictureLink', function(id) {
    console.log(id);
    return new Ember.Handlebars.SafeString("blub");
});
