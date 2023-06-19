import { GithubOutlined } from '@ant-design/icons';
import { DefaultFooter } from '@ant-design/pro-components';
const Footer: React.FC = () => {
  const defaultMessage = '唯多出品';
  const currentYear = new Date().getFullYear();
  return (
    <DefaultFooter
      copyright={`${currentYear} ${defaultMessage}`}
      links={[
        {
          key: 'que',
          title: '我的语雀',
          href: 'https://www.yuque.com/zweirdo',
          blankTarget: true,
        },
        {
          key: 'Ant Design',
          title: '我的博客',
          href: 'https://2weirdo.github.io',
          blankTarget: true,
        },
        {
          key: 'github',
          title: <><GithubOutlined /> weirdo Github</>,
          href: 'https://github.com/2WeirDo/',
          blankTarget: true,
        },

      ]}
    />
  );
};
export default Footer;
