import React, { useState } from "react";
import Card from "../UI/Card";
import ExpensesFilter from "./ExpensesFilter";
import ExpensesList from "./ExpensesList"
import ExpensesChar from "./ExpenseChart";
import "./Expenses.css";
import Pagination from "../Pagination/Pagination";

const Expenses = (props) => {
  const [filteredYear, setFilteredYear] = useState("2021");

  const filterChangeHandler = (selectedYear) => {
    setFilteredYear(selectedYear);
  };

  const filteredExpenses = props.items.filter((expense) => {
    return expense.date.getFullYear().toString() === filteredYear;
  });

  const movePage = (page) =>{
    return props.dataPages(page);
  }

  //<ExpensesList items={filteredExpenses}/>
  return (
      <div>
        <Card className="expenses">
          <ExpensesFilter
              selected={filteredYear}
              onChangeFilter={filterChangeHandler} />
          <ExpensesChar expenses={filteredExpenses}/>

          <Pagination
                data={filteredExpenses}
                RenderComponent={ExpensesList}
                totalData={props.totalData}
                dataLimit={10}
                movePages={movePage}
            />
        </Card>
      </div>
  );
};

export default Expenses;
