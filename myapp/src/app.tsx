// app.tsx 项目全局入口文件，定义了整个项目中使用的公共数据（比如用户信息）
import Footer from '@/components/Footer';
import RightContent from '@/components/RightContent';
import { BookOutlined, LinkOutlined } from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import { PageLoading, SettingDrawer } from '@ant-design/pro-components';
import type { RequestConfig, RunTimeLayoutConfig } from 'umi';
import { history, Link } from 'umi';
import defaultSettings from '../config/defaultSettings';
import { currentUser as queryCurrentUser } from './services/ant-design-pro/api';

const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/user/login';
/**
 * 无需用户登录态的页面
 */
const NO_NEED_LOGIN_WHITE_LIST = ['/user/register', loginPath];

/** 获取用户信息比较慢的时候会展示一个 loading */
export const initialStateConfig = {
  loading: <PageLoading />,
};

// 设置超时时间的目的是为了控制请求的响应时间，避免长时间等待导致的页面卡顿或无响应状态。
export const request: RequestConfig = {  
  timeout: 1000000,
};
/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{  // 用于获取应用程序的初始状态
  settings?: Partial<LayoutSettings>;
  currentUser?: API.CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {

  const fetchUserInfo = async () => {
    try {
            // 注意import { currentUser as queryCurrentUser } from './services/ant-design-pro/api';
      const user = await queryCurrentUser();  // 使用 queryCurrentUser 方法获取当前用户信息。  
      return user; 
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };
  // 如果不是登录页面，执行
  if (NO_NEED_LOGIN_WHITE_LIST.includes(history.location.pathname)) {
    return {
      // 取消下一行的 ts 错误
      // @ts-ignore  
      fetchUserInfo,
      settings: defaultSettings,
    };
  }
  const currentUser = await fetchUserInfo();
  const actualUser = currentUser?.data;
  return {
    // @ts-ignore
    fetchUserInfo,
    currentUser: actualUser,
    settings: defaultSettings,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {  // 导出了一个函数 layout，用于配置项目的布局和页面渲染逻辑
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: { // 水印
      content: initialState?.currentUser?.username,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;

      // if (!initialState?.currentUser && location.pathname !== loginPath) {
      //   if(location.pathname != '/user/register')
      //   history.push(loginPath);
      // }
      // 如果没有登录，重定向到 login
      
      if (NO_NEED_LOGIN_WHITE_LIST.includes(location.pathname)) {
        return;
      }
      if (!initialState?.currentUser) {
        history.push(loginPath);
      }
    },
    // links: isDev
    //   ? [
    //       <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
    //         <LinkOutlined />
    //         <span>OpenAPI 文档</span>
    //       </Link>,
    //       <Link to="/~docs" key="docs">
    //         <BookOutlined />
    //         <span>业务组件文档</span>
    //       </Link>,
    //     ]
    //   : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children: any, props: { location: { pathname: string | string[] } }) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {!props.location?.pathname?.includes('/login') && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};
