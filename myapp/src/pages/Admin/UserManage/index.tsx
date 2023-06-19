// import { searchUsers } from '@/services/ant-design-pro/api';
// import type { ActionType, ProColumns } from '@ant-design/pro-components';
// import { ModalForm, ProForm, ProFormSelect, ProFormText, ProTable, TableDropdown } from '@ant-design/pro-components';
// import { useRef } from 'react';
// import { Button, Image, Popconfirm, Tag, message } from 'antd';
// import { selectAvatarUrl, selectGender, selectUserRole, selectUserStatus } from '@/constants';
import {ActionType, ModalForm, ProColumns, ProForm, ProFormSelect, ProFormText } from '@ant-design/pro-components';
import { ProTable } from '@ant-design/pro-components';
import { Button, Image, Tag } from 'antd';
import { useRef } from 'react';
import { deleteUser, searchUsers, updateUserInfoByAdmin} from "@/services/ant-design-pro/api";
import { message, Popconfirm } from 'antd';
import {selectAvatarUrl, selectGender, selectUserRole, selectUserStatus} from "@/constants";
export const waitTimePromise = async (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

//定义等待时间函数
export const waitTime = async (time: number = 100) => {
  await waitTimePromise(time);
};
const columns: ProColumns<API.CurrentUser>[] = [
  {
    dataIndex: 'id',
    valueType: 'indexBorder',
    width: 46,
    align: "center",
  },
  {
    title: '用户名',
    dataIndex: 'username',
    copyable: true,
    ellipsis: true,
    align: "center",
  },
  {
    title: '用户账户',
    dataIndex: 'userAccount',
    copyable: true,
    align: "center",
  },
  {
    title: '头像',
    dataIndex: 'avatarUrl',
    align: "center",
    render: (_, record) => (
      <div>
        <Image src={record.avatarUrl} width={66} />
      </div>
    ),
    copyable: true,
  },
  {
    title: '性别',
    dataIndex: 'gender',
    align: "center",
    // 枚举
    valueType: 'select',
    valueEnum: {
      0: { text: <Tag color="success">男</Tag> },
      1: { text: <Tag color="error">女</Tag> },
    },
  },
  // {
  //   title: '电话',
  //   dataIndex: 'phone',
  //   copyable: true,
  //   align: "center",
  // },
  {
    title: '邮件',
    dataIndex: 'email',
    copyable: true,
    align: "center",
    ellipsis: true,
  },
  {
    title: '状态',
    dataIndex: 'userStatus',
    align: "center",
    valueEnum: {
      0: { text: <Tag color="success">正常</Tag>, status: 'Success' },
      1: { text: <Tag color="warning">注销</Tag>, status: 'Default' },
      2: { text: <Tag color="error">封号</Tag>, status: 'Error' },
    },
  },
  {
    title: '星球编号',
    dataIndex: 'planetCode',
    copyable: true,
    align: "center",
  },
  {
    title: '角色',
    dataIndex: 'userRole',
    valueType: 'select',
    align: "center",
    valueEnum: {
      0: { text: '普通用户', status: 'Default' },
      1: {
        text: '管理员',
        status: 'Success',
      },
    },
  },
  {
    title: '创建时间',
    dataIndex: 'createTime',
    valueType: 'date', // dateTime
    align: "center",
  },

//   {
//     title: '操作',
//     valueType: 'option',
//     key: 'option',
//     render: (text, record, _, action) => [
//       <a
//         key="editable"
//         onClick={() => {
//           action?.startEditable?.(record.id);
//         }}
//       >
//         编辑
//       </a>,
//       <a href={record.url} target="_blank" rel="noopener noreferrer" key="view">
//         查看
//       </a>,
//       <TableDropdown
//         key="actionGroup"
//         onSelect={() => action?.reload()}
//         menus={[
//           { key: 'copy', name: '复制' },
//           { key: 'delete', name: '删除' },
//         ]}
//       />,
//     ],
//   },
// ];
{
  title: '操作',
  align: 'center',
  valueType: 'option',
  key: 'option',
  render: (text, record, _, action) => [
    <ModalForm<API.CurrentUser>
      title="编辑用户信息"
      trigger={<Button type="link">编辑</Button>}
      autoFocusFirstInput
      modalProps={{
        destroyOnClose: true,
        onCancel: () => console.log('run'),
      }}
      submitTimeout={2000}
      onFinish={async (values) => {
        await waitTime(1000);
        //点击了提交
        console.log('values 的值为-------');
        //发起请求
        values.id = record.id;
        const isModify = await updateUserInfoByAdmin(values);
        if (isModify) {
          message.success('提交成功');
          // 刷新用户信息表单
          location.reload();
          return true;
        }
        return false;
      }}
    >
      <ProForm.Group>
        <ProFormText
          width="md"
          name="username"
          label="用户名"
          placeholder="请输入用户名"
          initialValue={record.username}
        />
        <ProFormText
          width="md"
          name="userAccount"
          label="用户账户"
          placeholder="请输入账户"
          initialValue={record.userAccount}
        />
        <ProFormSelect
          width="md"
          name="gender"
          label="性别"
          options={selectGender}
          placeholder="请选择性别"
          initialValue={record.gender}
          rules={[
            {
              required: true,
              message: '请选择性别',
            },
          ]}
        />
        <ProFormText
          width="md"
          name="phone"
          label="手机号"
          placeholder="请输入手机号"
          initialValue={record.phone}
        />
        <ProFormText
          width="md"
          name="email"
          label="邮箱"
          placeholder="请输入邮箱"
          
          initialValue={record.email}
        />
        <ProFormSelect
          width="md"
          name="userStatus"
          label="用户状态"
          options={selectUserStatus}
          initialValue={record.userStatus}
          placeholder={'选择用户状态'}
          rules={[
            {
              required: true,
              message: '请选择用户状态',
            },
          ]}
        />
        <ProFormSelect
          width="md"
          name="avatarUrl"
          label="用户头像"
          options={selectAvatarUrl}
          placeholder={'请选择用户头像 '}
          initialValue={record.avatarUrl}
          rules={[
            {
              required: true,
              message: '请输入选择用户头像!',
            },
          ]}
        />
        <ProFormSelect
          width="md"
          name="userRole"
          label="用户角色"
          options={selectUserRole}
          initialValue={record.userRole}
          placeholder={'选择用户角色'}
          rules={[
            {
              required: true,
              message: '请选择用户角色',
            },
          ]}
        />
      </ProForm.Group>
    </ModalForm>,
<a key="view">
  <Popconfirm
    title="删除用户"
    description="你确定要删除吗?"
    onConfirm={async (e) => {
      console.log(e);
      console.log(record.id);
      const id = record.id;
      const isdelete = await deleteUser({ id: id });
      if (isdelete) {
        message.success('删除成功');
        //这个刷新是无奈之举，如果不这样就不知道怎么去刷新那个用户信息表单
        location.reload();
      } else {
        message.error('删除失败');
      }
    }}
    onCancel={(e) => {}}
    okText="Yes"
    cancelText="No"
  >
    <Button type="link" danger>
      删除
    </Button>
  </Popconfirm>
</a>,
],
},
];


export default () => {
  const actionRef = useRef<ActionType>();
  return (
    <ProTable<API.CurrentUser>
      columns={columns}
      actionRef={actionRef}
      cardBordered
      //获取后端的数据，返回到表格
      request={async (params = {}, sort, filter) => {
        console.log(sort, filter);
        const userList = await searchUsers();
        return {
          data: userList
        }
      }}
      editable={{
        type: 'multiple',
      }}
      columnsState={{
        persistenceKey: 'pro-table-singe-demos',
        persistenceType: 'localStorage',
        onChange(value) {
          console.log('value: ', value);
        },
      }}
      rowKey="id"
      search={{
        labelWidth: 'auto',
      }}
      options={{
        setting: {
          listsHeight: 400,
        },
      }}
      form={{
        // 由于配置了 transform，提交的参与与定义的不同这里需要转化一下
        syncToUrl: (values, type) => {
          if (type === 'get') {
            return {
              ...values,
              created_at: [values.startTime, values.endTime],
            };
          }
          return values;
        },
      }}
      pagination={{
        pageSize: 5,
        onChange: (page) => console.log(page),
      }}
      dateFormatter="string"
      headerTitle="用户管理"
    />
  );
};



