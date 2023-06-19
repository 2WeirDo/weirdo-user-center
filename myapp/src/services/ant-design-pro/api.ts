// @ts-ignore
/* eslint-disable */

// 这段代码是前端的 API 请求函数，用于向后端发送请求并获取数据。

import  request  from '@/plugins/globalRequest';

/** 获取当前的用户 GET /api/user/current */
export async function currentUser(options?: { [key: string]: any }) {
  // 导入了一个名为 request 的全局请求插件，用于发送网络请求（通常基于 Axios 封装）。
  return request<API.BaseResponse<API.CurrentUser>>('/api/user/current', {
    // 每个函数通过调用 request 方法发送网络请求，并指定请求的 URL、请求方法、请求头、请求体等参数。
    // 使用泛型 <T> 来指定请求的返回数据类型，其中 API.BaseResponse<T> 表示后端返回的数据格式, API.CurrentUser 表示当前用户的数据类型。
    // request 相当于 axios 的封装
    method: 'GET',
    ...(options || {}),  // 使用展开运算符 ... 将 options 对象中的属性合并到请求参数中。
  });
}
// 这段代码的作用是通过调用 request 插件发送 GET 请求，获取当前用户的信息，并返回一个 Promise 对象。
// 使用者可以通过 await 关键字等待该 Promise 对象的结果，从而获取后端返回的数据。


/** 退出登录接口 POST /api/user/logout */
export async function outLogin(options?: { [key: string]: any }) {
  return request<API.BaseResponse<number>>('/api/user/logout', {  // 数据类型为 API.BaseResponse<number>，表示注销操作的结果。
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/user/login */
export async function login(body: API.LoginParams, options?: { [key: string]: any }) { 
   // 参数 body 是一个对象，表示登录请求的参数，类型为 API.LoginParams。
   // 参数 options 是一个可选的对象，用于配置请求的其他参数。
  return request<API.BaseResponse<API.LoginResult>>('/api/user/login', {
    // API.BaseResponse 表示后端返回的数据格式，而 API.LoginResult 表示登录操作的结果。
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',  // 表示请求体的数据类型为 JSON。
    },
    data: body,  // 登录请求的参数，通过 data 字段传递给后端。
    ...(options || {}),
  });
}

/** 删除用户 Post /api/user/delete */
export async function deleteUser(body: API.DeleteParam, options?: { [key: string]: any }) {
  return request<API.BaseResponse<boolean>>('/api/user/delete2', {
    method: 'Post',
    data: body,
    ...options,
  });
}

/** 注册接口 POST /api/user/register */
export async function register(body: API.RegisterParams, options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.RegisterResult>>('/api/user/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 搜素用户 GET /api/user/search */
export async function searchUsers(options?: { [key: string]: any }) {
  return request<API.BaseResponse<API.CurrentUser[]>>('/api/user/search2', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 新增用户接口 POST /api/user/add */
export async function create(body: API.CreateParams, options?: { [key: string]: any }) {
  return request<API.BaseResponse<Boolean>>('/api/user/add', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 修改密码接口 POST /api/user/update/password */
export async function modifyPassword(body: API.ModifyPasswordParam, options?: { [key: string]: any }) {
  return request<API.BaseResponse<boolean>>('/api/user/update/password', {
    method: 'POST',
    data: body,
    ...options,
  });
}

/** 修改用户信息接口 POST /api/user/update/myUser */
export async function userModify(body: API.ModifyUserParams, options?: { [key: string]: any }) {
  return request<API.BaseResponse<boolean>>('/api/user/update/myUser', {
    method: 'POST',
    data: body,
    ...options,
  });
}


/** 修改用户接口 POST /api/login/account */
export async function updateUserInfoByAdmin(body: API.CurrentUser, options?: { [key: string]: any }) {
  return request<API.BaseResponse<boolean>>('/api/user/update/admin', {
    method: 'POST',
    data: body,
    ...options,
  });
}


/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: { [key: string]: any }) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: { [key: string]: any },
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: { [key: string]: any }) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: { [key: string]: any }) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}
