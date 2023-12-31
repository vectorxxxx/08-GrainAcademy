## Build Setup

``` bash
# install dependencies
$ npm install # Or yarn install

# serve with hot reload at localhost:3000
$ npm run dev

# build for production and launch server
$ npm run build
$ npm start

# generate static project
$ npm run generate

# install corresponding version
$ npm install vue-awesome-swiper@2
```

## 1、asyncData 跟 created 方法中有何区别？

`asyncData` 和 `created` 都是 Vue.js 中的生命周期钩子函数，它们在 Vue 实例被创建之后立即执行，但是它们之间有一些关键区别。

1. 返回值不同：`asyncData` 钩子函数返回一个 Promise 对象，而 `created` 钩子函数返回 undefined。
2. 执行时机不同：`asyncData` 钩子函数会在实例被创建之后立即执行，而 `created` 钩子函数会在实例被创建之后等待数据准备好之后再执行。
3. 处理异步数据的不同：`asyncData` 钩子函数主要用于处理异步数据，例如从服务器获取数据。而 `created` 钩子函数主要用于处理一些其他操作，例如初始化数据、创建 DOM 节点等。

在实际应用中，你可以根据需求选择使用 `asyncData` 钩子函数还是 `created` 钩子函数。通常情况下，如果需要处理异步数据，那么使用 `asyncData` 钩子函数会更为合适。但是，如果需要处理其他操作，那么使用 `created` 钩子函数会更为合适。

下面是一个简单的例子，展示了如何使用 `asyncData` 和 `created` 钩子函数：
```javascript
export default {
  asyncData() {
    return new Promise((resolve) => {
      setTimeout(() => {
        resolve({ message: 'Hello, world!' });
      }, 1000);
    });
  },
  created() {
    console.log('Data is ready!');
  },
  mounted() {
    console.log('Component is mounted!');
  }
};
```
在这个例子中，`asyncData` 钩子函数会立即执行，返回一个 Promise 对象，然后 `created` 钩子函数会等待 Promise 对象解析完成后再执行，并输出 "Data is ready!"。
