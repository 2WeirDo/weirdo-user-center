// @ts-ignore
/* eslint-disable */

// 这段代码是一个 TypeScript 声明文件，用于定义前端应用与后端接口之间的数据类型和接口规范。
// 通过在前端代码中引入这个声明文件，可以提供类型检查和代码提示的功能，
// 使前端开发人员更方便地使用后端提供的接口，并减少因数据类型不匹配而导致的错误。

declare namespace API {
  // 返回给前端的
  type CurrentUser = {
    unreadCount: CurrentUser | undefined;
    url: string | undefined;
    id: number;
    userAccount: string;
    username: string;
    avatarUrl?: string;
    gender: number;
    phone: string;
    email: string;
    userStatus: number;
    planetCode: string;
    createTime: Date;
    userRole: number;
  };

  type LoginResult = {
    status?: string;
    type?: string;
    currentAuthority?: string;
  };

  type RegisterResult = number;

  type PageParams = {
    current?: number;
    pageSize?: number;
  };

  type RuleListItem = {
    key?: number;
    disabled?: boolean;
    href?: string;
    avatar?: string;
    name?: string;
    owner?: string;
    desc?: string;
    callNo?: number;
    status?: number;
    updatedAt?: string;
    createdAt?: string;
    progress?: number;
  };

  /**
   * 对接后端的通用返回类
   */
  type BaseResponse<T> = {
    code: number,
    data: T,
    message: string,
    description: string,
  }

    /**
   * 删除的参数
   */
    type DeleteParam = {
      id:number;
    };

      /**
   * 创建用户变量
   */
  type CreateParams = {
    username?: string;
    userAccount: string;
    userPassword?: string;
    avatarUrl?: string;
    gender:number;
    phone: string;
    email: string;
    userStatus: number;
    createTime: Date;
    userRole: number;
  };

    /**
   * 修改密码
   */
    type ModifyPasswordParam = {
      userPassword: string;
      newPassword: string;
    };
  
    type ModifyUserParams = {
      avatarUrl: string
      email: string
      gender: number
      id: number
      phone: string
      username: string
    }

  type RuleList = {
    data?: RuleListItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type FakeCaptcha = {
    code?: number;
    status?: string;
  };

  type LoginParams = {
    userAccount?: string;
    userPassword?: string;
    autoLogin?: boolean;
    type?: string;
  };

  type RegisterParams = {
    userAccount?: string;
    userPassword?: string;
    checkPassword?: string;
    planetCode?: string;
    type?: string;
  };

  type ErrorResponse = {
    /** 业务约定的错误码 */
    errorCode: string;
    /** 业务上的错误信息 */
    errorMessage?: string;
    /** 业务上的请求是否成功 */
    success?: boolean;
  };

  type NoticeIconList = {
    data?: NoticeIconItem[];
    /** 列表的内容总数 */
    total?: number;
    success?: boolean;
  };

  type NoticeIconItemType = 'notification' | 'message' | 'event';

  type NoticeIconItem = {
    id?: string;
    extra?: string;
    key?: string;
    read?: boolean;
    avatar?: string;
    title?: string;
    status?: string;
    datetime?: string;
    description?: string;
    type?: NoticeIconItemType;
  };
}
