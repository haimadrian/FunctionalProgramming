const expenseManagement = 'http://localhost:3000';
const userManagement = 'http://localhost:9001';
const expenseStatistics = 'http://localhost:3002';

const urls = {
    userSignUp: `${userManagement}/api/user/signup`,
    userSignOut: `${userManagement}/api/user/signout`,
    userInfo: `${userManagement}/api/user/info`
}

export default urls;