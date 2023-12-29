### 1、`$router` 和 `$route` 的区别

- `this.$router` 是 Vue Router 实例，用来导航跳转和管理路由的对象，可以通过它来进行编程式导航和访问路由的信息
- `this.$route` 是当前活跃的路由信息对象，包含了当前路由的各种信息，如路径、参数、查询参数、hash 等，可以通过它来访问当前路由的信息

### 2、vue 中 process.env 表示什么意思

在 Vue.js 中,`process.env` 是一个对象,其中包含一些环境变量,这些变量可以用于条件地配置应用程序。这些变量通常由服务器或构建工具提供,以便在开发和生产环境中使用不同的设置。

在 Vue.js 中,您可以使用 `process.env` 来检查某些条件,例如:

```javascript
if (process.env.NODE_ENV === "development") {
  // 开发环境下的代码
} else if (process.env.NODE_ENV === "production") {
  // 生产环境下的代码
}
```

在这个例子中,我们使用 `process.env.NODE_ENV` 来检查当前应用程序运行在开发还是生产环境下。如果 `NODE_ENV` 的值为 `'development'`,则执行开发环境下的代码;如果 `NODE_ENV` 的值为 `'production'`,则执行生产环境下的代码。

`process.env` 对象中还有其他环境变量,例如 `BASE_URL` 和 `API_URL`,这些变量可以用于配置应用程序的 URL。

请注意,`process.env` 对象在 Vue.js 组件中是全局的,这意味着您可以在组件的任意位置使用它,而不需要使用 `this` 引用。

### 3、一些命令

```shell
# nvm
nvm list
nvm install [version]
nvm use [version]

# npm
npm get registry
npm config set registry https://registry.npmmirror.com
npm install
npm run dev
```

