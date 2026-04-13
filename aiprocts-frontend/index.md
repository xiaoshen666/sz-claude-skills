## 工作流组件

```jsx
import WorkflowInfo from '../CdsWorkflow/WorkflowInfo/index';
import WorkflowHandle from '../CdsWorkflow/WorkflowHandle/index';
import WorkflowAction from '../CdsWorkflow/WorkflowAction/index';
import CdsToolbar from '../CdsToolbar/index';
import WorkflowComponent from '../CdsWorkflow/index';

export default (props) => {
  const handleSubmitWorkflow = (data) => {
    // 在这里处理工作流的提交保存驳回和作废等操作
    console.log(data);
  };
  const handleReadyWorkflow = (data) => {
    //处理工作流的相关数据
    console.log(data);
  };
  return (
    <WorkflowComponent
      onSubmit={handleSubmitWorkflow}
      onReady={handleReadyWorkflow}
    >
      <div className="headerContent">
        {
          <CdsToolbar
            open={true}
            id={'workflowHeade'}
            viewname={'标题'}
            WorkflowAction={
              <WorkflowAction
                entrust={true}
                recall={true}
                remind={true}
                terminate={true}
              />
            }
            WorkflowInfo={<WorkflowInfo />}
          />
        }
      </div>
      <div className="mainContent">{'这是页面的主要内容'}</div>
      <div className="footerConent">{<WorkflowHandle />}</div>
    </WorkflowComponent>
  );
};
```

### WorkflowComponent

| 属性     | 值类型             | 说明                                                                                                                                                                                                            |
| -------- | ------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| children | React.ReactNode[]  | 内容页面                                                                                                                                                                                                        |
| onReady  | (data:any) => void | 回调函数, 可获取完整的组件参数数据,包含已请求的接口数据,如(工作流状态 status、工作流 taskId、 工作流 processId、工作流 appId 、工作流 processKey、工作流催办信息 remindLog 以及当前工作流节点信息 workflowData) |
| onSubmit | (data:any) => any  | 回调函数, 处理包括保存、提交、驳回及催办等按钮操作                                                                                                                                                              |

### WorkflowAction

**注意：WorkflowAction 控件中委托按钮、撤回按钮展示与否的配置当且仅当在工作流组态配置后生效，如设置了 entrust 为 true，但是工作流组态并未配置允许委托，那委托按钮也不会展示**

| 属性      | 值类型  | 默认值 | 说明                        |
| --------- | ------- | ------ | --------------------------- |
| entrust   | boolean | false  | 是否展示委托按钮,默认不展示 |
| recall    | boolean | false  | 是否展示撤回按钮,默认不展示 |
| remind    | boolean | false  | 是否展示催办按钮,默认不展示 |
| terminate | boolean | false  | 是否展示作废按钮,默认不展示 |

### WorkflowInfo

| 属性    | 值类型  | 默认值 | 说明                                     |
| ------- | ------- | ------ | ---------------------------------------- |
| process | boolean | true   | 是否展示工作流流程查看按钮，默认展示     |
| log     | boolean | true   | 是否展示工作流审批记录查看按钮，默认展示 |
