/**
 * 在生产环境 代理是无法生效的，所以这里没有生产环境的配置
 * -------------------------------
 * The agent cannot take effect in the production environment
 * so there is no configuration of the production environment
 * For details, please see
 * https://pro.ant.design/docs/deploy
 */
// ，为什么会跨域？因为我们当前前端的浏览器里的地址，它是8000端口, 但是我们的后端，它是8080端口，端口不一样，就是跨域的。
// 跨域的话，有很多种方式解决，要么搭一个代理，要么在你的后端去支持跨域，但后端支持跨域很不安全
// 在前端开发中，为了避免跨域请求的限制，常常需要配置代理，将前端的请求转发到后端的接口。这样可以解决在开发环境中，前端和后端不在同一域的问题。
export default {
  dev: {
    // localhost:8000/api/** -> https://preview.pro.ant.design/api/**
    '/api/': {
      // 要代理的地址
      target: 'http://localhost:8080',
      // 配置了这个可以从 http 代理到 https
      // 依赖 origin 的功能可能需要这个，比如 cookie
      changeOrigin: true,
    },
  },  
  test: {
    '/api/': {
      target: 'https://proapi.azurewebsites.net',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
  pre: {
    '/api/': {
      target: 'your pre url',
      changeOrigin: true,
      pathRewrite: { '^': '' },
    },
  },
};
