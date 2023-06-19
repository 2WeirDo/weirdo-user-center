import { Settings as LayoutSettings } from '@ant-design/pro-components';

const Settings: LayoutSettings & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'dark',
  // 拂晓蓝
  primaryColor: '#a18cd1',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: 'weirdo花园--- ',
  pwa: false,
  logo: 'https://image.meiye.art/pic_s3jj7VznX1ucJREQTV1iZ?imageMogr2/thumbnail/620x/interlace/1',
  iconfontUrl: '',
};

export default Settings;
