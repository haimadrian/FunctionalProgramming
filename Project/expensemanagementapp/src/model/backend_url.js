const expenseManagement = 'https://expenseapphit.herokuapp.com';
const userManagement = 'https://expenseapphituser.herokuapp.com';
const expenseStatistics = 'https://expenseapphitstat.herokuapp.com';

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