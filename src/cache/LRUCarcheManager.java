package cache;


import java.util.*;

/**
 * LRU是最近最少使用
 */
public class LRUCarcheManager {

	static SortedMap<String,Cache> cacheMap=new TreeMap<String,Cache>();
	static final int MAX_CACHE_NUM=5;//最大五个缓存

	private static class ValueComparator implements Comparator<Map.Entry<String,Cache>>
	{
		@Override
		public int compare(Map.Entry<String,Cache> m, Map.Entry<String,Cache> n)
		{
			return (int) (n.getValue().tagDate.getTime()-m.getValue().tagDate.getTime());
		}
	}

	public static Cache getCache(String id)
	{
		if(cacheMap.get(id)==null)
		{
			Object val=getFromDB(id);
			cacheMap.put(id, new Cache(id,val));
		}
		Cache res=cacheMap.get(id);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		res.tagDate=new Date();
		return cacheMap.get(id);
	}

	public static void putCache(Cache cache)
	{
		cacheMap.put(cache.id, cache);
	}

	public static Object getFromDB(String id)
	{
		System.out.println("缓慢地从内存中读取id="+id+"对应的数据。。。");
		return new String("value"+id);
	}

	public static void refreshCaches()
	{
		System.out.println("刷新缓存。。。");

		List<Map.Entry<String,Cache>> list=new ArrayList();
		list.addAll(cacheMap.entrySet());
		ValueComparator comparator=new ValueComparator();
		Collections.sort(list,comparator);

		for(Iterator<Map.Entry<String,Cache>> itea=list.iterator();itea.hasNext();)
		{
			System.out.println(itea.next());
		}

		for(int i=MAX_CACHE_NUM;i<list.size();i++)
		{
			String id=(String) list.get(i).getKey();
			Object val=getFromDB(id);
			cacheMap.put(id, new Cache(id,val));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Cache cache1=new Cache("1","value1");
		Cache cache2=new Cache("2","value2");
		Cache cache3=new Cache("3","value2");
		Cache cache4=new Cache("4","value2");
		Cache cache5=new Cache("5","value2");
		Cache cache6=new Cache("6","value2");
		Cache cache7=new Cache("7","value2");
		LRUCarcheManager.putCache(cache1);
		LRUCarcheManager.putCache(cache2);
		LRUCarcheManager.putCache(cache3);
		LRUCarcheManager.putCache(cache4);
		LRUCarcheManager.putCache(cache5);
		LRUCarcheManager.putCache(cache6);
		LRUCarcheManager.putCache(cache7);
		LRUCarcheManager.getCache("5");
		LRUCarcheManager.getCache("6");
		LRUCarcheManager.getCache("6");
		refreshCaches();

		Thread refreshTD=new Thread()
		{
			public void run()
			{
				while(true)
				{
					refreshCaches();
					try {
						Thread.sleep(1000);//每一秒刷新一次
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
				refreshTD.start();
	}
}

class Cache
{
	String id;//相当于主键
	Object val;
	Date tagDate;//标记日期，标定什么时候使用的


	public Cache(String id,Object val)
	{
		this.id=id;
		this.val=val;
		this.tagDate=new Date();
	}

	public void setValue(Object val)
	{
		this.val=val;
		this.tagDate=new Date();
	}

	public void showInfo()
	{
		System.out.println("Cache的ID是:   "+id+"   Cache的值是:   "+val);
	}

	public void setTagDate(Date dt)
	{
		this.tagDate=dt;
	}

}
