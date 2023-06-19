import { PageContainer } from '@ant-design/pro-components';
import { Alert, Card, Typography } from 'antd';
import styles from './Welcome.less';
import { Image } from 'antd';

import React from 'react';
const CodePreview: React.FC = ({ children }) => (
  <pre className={styles.pre}>
    <code>
      <Typography.Text copyable>{children}</Typography.Text>
    </code>
  </pre>
);
const Welcome: React.FC = () => {
  return (
    <PageContainer>
      <Card>
        <Alert
          message={'欢迎使用用户管理，你可以记录和管理自己的信息~'}
          type="success"
          showIcon
          banner
          style={{
            margin: -12,
            marginBottom: 24,
          }}
        />
        <Typography.Text strong>
          <a
            href="https://procomponents.ant.design/components/table"
            rel="noopener noreferrer"
            target="__blank"
          >
            管理用户
          </a>
        </Typography.Text>

        <CodePreview><p>你可以</p>
        1.创建用户<p/>
        2.查询用户信息<p/>
        3.修改用户信息<p/>
        4.删除用户信息</CodePreview>

      </Card>
      <Image.PreviewGroup>
    <Image width={400} src="https://image.meiye.art/pic_9S4QGtmidkUy-aHqlFpEe?imageMogr2/thumbnail/620x/interlace/1" />
    <Image width={400} src="https://image.meiye.art/pic_cNQXyb7tVQ-tnIo_83izh?imageMogr2/thumbnail/620x/interlace/1"/>
    <Image width={400} src="https://image.meiye.art/pic_orDil37spK-THURmZk0cd?imageMogr2/thumbnail/620x/interlace/1"/>
    <br /><br /><br />
    <Image width={400} src="https://image.meiye.art/pic_DTe7o1E80eGm_tyYUF5fe?imageMogr2/thumbnail/620x/interlace/1"/>
    <Image width={400} src="https://image.meiye.art/pic_H-PT0pt_OgdgCIrRnJW5S?imageMogr2/thumbnail/620x/interlace/1"/>
    <Image width={400} src="https://image.meiye.art/pic_aNILmw9iUoVrv7WuRtGrj?imageMogr2/thumbnail/620x/interlace/1"/>
    <br /><br /><br />
    <Image width={400} src="https://image.meiye.art/pic_i7WsExclINLTD9bw9yKEz?imageMogr2/thumbnail/620x/interlace/1"/>
    <Image width={400} src="https://image.meiye.art/pic_v-JB_Ns0T4XBWUqsHkHCN?imageMogr2/thumbnail/620x/interlace/1"/>
    <Image width={400} src="https://image.meiye.art/pic_4rDvyW39NCv5nMPhsGOjA?imageMogr2/thumbnail/620x/interlace/1"/>


  </Image.PreviewGroup>
    </PageContainer>
    
  );
};
export default Welcome;
