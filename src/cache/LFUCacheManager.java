package cache;


import java.util.*;

/**
 * LFU是最近最不常用
 * @author sunpengwei
 */
public class LFUCacheManager {

	static SortedMap<String,Cache1> cacheMap=new TreeMap<String,Cache1>();
	static final int MAX_CACHE_NUM=5;//最大五个缓存

	private static class ValueComparator implements Comparator<Map.Entry<String,Cache1>>
	{
		@Override
		public int compare(Map.Entry<String,Cache1> m, Map.Entry<String,Cache1> n)
		{
			return n.getValue().usedcount-m.getValue().usedcount;
		}
	}

	public static Cache1 getCache(String id)
	{
		if(cacheMap.get(id)==null)
		{
			Object val=getFromDB(id);
			cacheMap.put(id, new Cache1(id,val));
		}
		Cache1 res=cacheMap.get(id);
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		res.addCount();
		return cacheMap.get(id);
	}

	public static void putCache(Cache1 cache)
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

		List<Map.Entry<String,Cache1>> list=new ArrayList();
		list.addAll(cacheMap.entrySet());
		ValueComparator comparator=new ValueComparator();
		Collections.sort(list,comparator);
		for(Iterator<Map.Entry<String,Cache1>> itea=list.iterator();itea.hasNext();)
		{
			System.out.println(itea.next());
		}
		for(int i=MAX_CACHE_NUM;i<list.size();i++)
		{
			String id=(String) list.get(i).getKey();
			Object val=getFromDB(id);
			cacheMap.put(id, new Cache1(id,val));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		Cache1 cache1=new Cache1("1","value1");
		Cache1 cache2=new Cache1("2","value2");
		Cache1 cache3=new Cache1("3","value2");
		Cache1 cache4=new Cache1("4","value2");
		Cache1 cache5=new Cache1("5","value2");
		Cache1 cache6=new Cache1("6","value2");
		Cache1 cache7=new Cache1("7","value2");
		LFUCacheManager.putCache(cache1);
		LFUCacheManager.putCache(cache2);
		LFUCacheManager.putCache(cache3);
		LFUCacheManager.putCache(cache4);
		LFUCacheManager.putCache(cache5);
		LFUCacheManager.putCache(cache6);
		LFUCacheManager.putCache(cache7);
		LFUCacheManager.getCache("1");
		LFUCacheManager.getCache("1");
		LFUCacheManager.getCache("2");
		LFUCacheManager.getCache("3");
		LFUCacheManager.getCache("3");
		LFUCacheManager.getCache("4");
		LFUCacheManager.getCache("4");
		LFUCacheManager.getCache("6");
		LFUCacheManager.getCache("5");
		LFUCacheManager.getCache("5");
		LFUCacheManager.getCache("6");
		LFUCacheManager.getCache("6");
		LFUCacheManager.getCache("3");
		LFUCacheManager.getCache("3");
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

class Cache1 {

	String id;//相当于主键
	Object val;
	Date startdt;//标记新建时间
	int usedcount=0;//标记使用次数


	public Cache1(String id,Object val)
	{
		this.id=id;
		this.val=val;
		this.startdt=new Date();
		usedcount++;
	}

	public void setValue(Object val)
	{
		this.val=val;
	}

	public void addCount()
	{
		usedcount++;
	}

	public void showInfo()
	{
		System.out.println("Cache的ID是:   "+id+"   Cache的值是:   "+val);
	}

	/**
	 * 往往刚刚新建的被访问机会是最少的
	 * @param o
	 * @return
	 */
	public int compareTo(Object o) {
		if(o instanceof Cache1)
		{
			Cache1 c=(Cache1) o;
			if(this.usedcount>c.usedcount)
				return 1;
			else
				return -1;
		}
		return 0;
	}

}