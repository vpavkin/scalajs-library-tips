// Creation
var homelessFork = Github.createFork("foo-fork", "Foo", 1);
var fork = Github.createFork("bar-fork", "Bar", 1, "http://bar.bar");
var user = Github.createUser("John", "http://john.img", [fork, homelessFork]);
console.log("homelessFork:", homelessFork.toString());

// props
console.log("fork.name:", fork.name);
console.log("fork.homepage:", fork.homepage);
console.log("user.repos:", user.repos);
console.log("user.repos names (map):", user.repos.map(function (repo) {
	return repo.name
}));

// sum types
console.log("fork.type:", fork.type);
console.log("fork.type == Github.Fork.type:", (fork.type == Github.Fork.type));
console.log("fork.type == Github.Origin.type:",
	(fork.type == Github.Origin.type));

// loading users
function loadAndLog (login) {
	console.log("Loading user " + login);
	Github.loadUser(login)
		.then(function (result) {
			console.log("Loaded user " + login);
			console.log("Name: ", result.name);
			console.log("Repos", result.repos.map(function (r) {
				return r.name
			}))
		}, function (error) {
			console.log("Error occured:", error)
		});
}

loadAndLog("vpavkin");
setTimeout(loadAndLog.bind(null, ""), 5000);
