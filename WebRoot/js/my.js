function getPaginate(page,totalPage) {
	var paginate = new Array();
	if(totalPage<=5) {	// 如果总页数小于等于5，则全部显示
		// 构造分页 
		for (var int = 1; int <= totalPage; int++) {
			var pageObject = {};
			pageObject.pageNum = int;
			pageObject.isNowPage = (page==int?1:0);
			paginate.push(pageObject);
		}
	} else {	// 如果总页数大于5则只显示部分页码
		if(page<=3) {
			for (var int = 1; int <= 5; int++) {
				var pageObject = {};
				pageObject.pageNum = int;
				pageObject.isNowPage = (page==int?1:0);
				paginate.push(pageObject);
			}
		} else {
			if(totalPage-page>=2){
				for (var int = page - 2; int <= page+2; int++) {
					var pageObject = {};
					pageObject.pageNum = int;
					pageObject.isNowPage = (page==int?1:0);
					paginate.push(pageObject);
				}
			} else {
				for (var int = totalPage - 4; int <= totalPage; int++) {
					var pageObject = {};
					pageObject.pageNum = int;
					pageObject.isNowPage = (page==int?1:0);
					paginate.push(pageObject);
				}
			}
		}
	}
	return paginate;
}