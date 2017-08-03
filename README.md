## Example How to Call Non-Trivial NPM Libraries in  lumo / clojurescript

Example of how to call a couple of NPM Libraries with different sets of function
signatures from (Clojurescript)[https://clojurescript.org/] in a
(Lumo)[https://github.com/anmonteiro/lumo] program.

### NPM Libraries Used

The two npm libraries are:

* (git-latest-semver-tag)[https://www.npmjs.com/package/git-latest-semver-tag]
* (simple-git)[https://www.npmjs.com/package/simple-git]

`git-latest-semver-tag` has a very simple calling signature. It just returns the
latest tag of the git repot that it is run in. In Javascript:

```javascript
var gitLatestSemverTag = require('git-latest-semver-tag');
 
gitLatestSemverTag(function(err, tag) {
  console.log(tag);
  //=> 'v1.0.0' 
});
```

The equivalent in Lumo / Clojurescript is:

```clojure
(def git-latest (js/require "git-latest-semver-tag"))
(git-latest (fn [err,tag] (js/console.log tag)))
```

`simple-git` is a much more complex library with many functions for doing git
operations on a repo. You can specify the repo path as an argument to the base
function of the library. 

It does require that you have the git program installed on the computer that
runs it.

This example just uses the `.tags` function. The "constructor" takes an argument:
>  workingDirPath, is optional defaulting to the current directory.

Our example uses the current directory
In Javascript:

```javascript
var simpleGit = require('simple-git')('.');
simpleGit.tags(function(err, tags) {
            console.log("Latest available tag: %s", tags.latest);
            });
```

The lumo / clojurescript equivalent:

```clojure
(def simple-git ((js/require "simple-git") ".")) 
(.tags simple-git  (fn [err,tags] (js/console.log "Latest available tag: " tags.latest)))
```

You can also specify the directory after the require in your code if you
initialize `simple-git` without calling the result of the require as a function:

```clojure
(def simple-git (js/require "simple-git"))
(.tags (simple-git ".")  (fn [err,tags] (js/console.log "Latest available tag: " tags.latest)))
```

Many (most?) NPM libraries are asynchronous which use callback functions to
return results. This is great for web apps, but generally a bummer for writing
scripts. The example program (`src/core.cljs`) uses core-async to get the
asynchronous results back into the main program thread.

### Setting Up

#### Clone the Repo 
Of course you need to:

```shell
git clone git@github.com:rberger/lumo-npm-clojurescript-example.git
cd lumo-npm-clojurescript-example
```

#### Install the NPM dependencies in your local repo

```shell
npm install
```

This will pull the npm libraries and dependencies into `node_modules`

#### Install Clojure / Clojurescript dependencies

We need to use the customized version of clojure/core.async called
(andare)[https://github.com/mfikes/andare] that works with bootstrapped
ClojureScript environments like (lumo)[https://github.com/anmonteiro/lumo].

The easiest way is to get andare into your local maven repo. The easiest way I
know to do that is to have a `project.clj` and use `lein deps` to get it and any
potential dependencies. This technique will work for any clojure or
clojurescript dependencies you may need in your lumo scripts.

This repo has a `project.clj` that is only used for getting these dependencies.
It is not used by lumo itself.

So you should run:

```shell
lein deps
```

#### Executing the program

To execute the program:

```shell
lumo -K -D andare:0.7.0 -c src -m lumo-npm-clojurescript-example.core
```

* `-K` will cache any dependencies in `.lumo_cache` This will speed up running
the command after the first time.
* `-D andare:0.7.0` tells `lumo` to pull in the dependency `andre:0.7.0` from
  your local maven repo (`~/.m2`)
* `-c  src` specifies the classpath for the `lumo` run
* `-m `

If you use the repo you don't need to do the following since the `package.json`
already exists but if you were doing this from scratch you would have to:

* To get the NPM libraries into the repo so they are able to be `require`d, you need to do the following:



