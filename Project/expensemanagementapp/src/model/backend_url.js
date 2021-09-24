const expenseManagement = 'http://localhost:9000';
const userManagement = 'http://localhost:9001';
const expenseStatistics = 'http://localhost:9002';

const urls = {
    userSignUp: `${userManagement}/api/user/signup`,
    userSignOut: `${userManagement}/api/user/signout`,
    userInfo: `${userManagement}/api/user/info`,
    expenseFetch: (page, limit) => `${expenseManagement}/api/expense/fetch/page/${page}/limit/${limit}`,
    totalDataCount: `${expenseManagement}/api/expense/count`,
    addExpense: `${expenseManagement}/api/expense`,
    getStatsMonthly: (year) => `${expenseStatistics}/api/statistics/year/${year}`,
    getStatsDaily: (year, month) => `${expenseStatistics}/api/statistics/year/${year}/month/${month}`
}

export default urls;