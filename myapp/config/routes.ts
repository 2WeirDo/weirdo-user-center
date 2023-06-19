export default [
  {
    path: '/user',
    layout: false,
    routes: [
      {
        name: '登录',
        path: '/user/login',
        component: './user/Login',
      },
      {
        name: '注册',
        path: '/user/register',
        component: './user/Register',
      },
      {
        component: './404',
      },
    ],
  },
  {
    path: '/welcome',
    name: 'weirdo花园',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/admin',
    name: '管理一下',
    icon: 'crown',
    access: 'canAdmin',
    // 只有管理员能访问
    component: './Admin',
    routes: [
      {
        path: '/admin/user-manage',
        name: '成员管理',
        icon: 'smile',
        component: './Admin/UserManage',
      },
      { 
        path: '/admin/user-create', 
        name: '扩充成员', icon: 'smile', 
        component: './Admin/CreateUser' 
      },
      {
        component: './404',
      },
    ],
  },
  {
    name: '我的信息',
    icon: 'table',
    path: '/list',
    component: './UserInfo',
  },
  {
    path: '/',
    redirect: '/welcome',
  },
  {
    component: './404',
  },
];
